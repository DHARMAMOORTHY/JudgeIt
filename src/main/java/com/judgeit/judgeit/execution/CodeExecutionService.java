package com.judgeit.judgeit.execution;

import com.judgeit.judgeit.Problem;
import com.judgeit.judgeit.ProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeExecutionService {
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;
    private final SandboxRunner sandboxRunner;

    public CodeExecutionService(ProblemRepository problemRepository,
                                TestCaseRepository testCaseRepository,
                                SandboxRunner sandboxRunner) {
        this.problemRepository = problemRepository;
        this.testCaseRepository = testCaseRepository;
        this.sandboxRunner = sandboxRunner;
    }

    @Transactional(readOnly = true)
    public ExecutionResult execute(ExecutionRequest request) {
        Problem problem = problemRepository.findById(request.problemId())
            .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + request.problemId()));

        List<TestCase> cases = testCaseRepository.findByProblem_IdOrderByIdAsc(problem.getId());
        if (cases.isEmpty()) {
            throw new IllegalStateException("No test cases are configured for this problem.");
        }

        List<ExecutionResult.TestCaseResult> results = new ArrayList<>();
        int passed = 0;
        for (TestCase testCase : cases) {
            SandboxRunner.RunResult run = sandboxRunner.run(
                request.language(), request.sourceCode(), testCase.getInputData());

            String actual = run.stdout() == null ? "" : run.stdout();
            String verdict;
            if (run.timedOut()) {
                verdict = "TIME_LIMIT_EXCEEDED";
            } else if (run.exitCode() != 0) {
                verdict = "RUNTIME_ERROR";
            } else if (normalize(actual).equals(normalize(testCase.getExpectedOutput()))) {
                verdict = "ACCEPTED";
                passed++;
            } else {
                verdict = "WRONG_ANSWER";
            }
            results.add(new ExecutionResult.TestCaseResult(testCase.getId(), verdict, actual));
            if (!"ACCEPTED".equals(verdict)) {
                return new ExecutionResult(problem.getId(), verdict, passed, cases.size(),
                    List.copyOf(results), "Execution stopped at the first failed test case.");
            }
        }
        return new ExecutionResult(problem.getId(), "ACCEPTED", passed, cases.size(),
            List.copyOf(results), "All test cases passed.");
    }

    private static String normalize(String output) {
        return output == null ? "" : output.replace("\r\n", "\n").stripTrailing();
    }
}
