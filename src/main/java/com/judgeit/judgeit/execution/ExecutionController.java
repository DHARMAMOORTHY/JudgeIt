package com.judgeit.judgeit.execution;

import com.judgeit.judgeit.ProblemRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExecutionController {
    private final CodeExecutionService executionService;
    private final TestCaseRepository testCaseRepository;
    private final ProblemRepository problemRepository;

    public ExecutionController(CodeExecutionService executionService,
                               TestCaseRepository testCaseRepository,
                               ProblemRepository problemRepository) {
        this.executionService = executionService;
        this.testCaseRepository = testCaseRepository;
        this.problemRepository = problemRepository;
    }

    @PostMapping("/executions")
    public ExecutionResult execute(@Valid @RequestBody ExecutionRequest request) {
        try {
            return executionService.execute(request);
        } catch (SandboxUnavailableException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        }
    }

    @GetMapping("/problems/{problemId}/test-cases")
    public List<TestCaseView> listTestCases(@PathVariable Long problemId) {
        if (!problemRepository.existsById(problemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found: " + problemId);
        }
        return testCaseRepository.findByProblem_IdOrderByIdAsc(problemId).stream()
            .map(testCase -> new TestCaseView(testCase.getId(), testCase.getInputData(),
                testCase.getExpectedOutput()))
            .toList();
    }

    @PostMapping("/problems/{problemId}/test-cases")
    @ResponseStatus(HttpStatus.CREATED)
    public TestCaseView createTestCase(@PathVariable Long problemId,
                                       @Valid @RequestBody TestCaseRequest request) {
        var problem = problemRepository.findById(problemId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Problem not found: " + problemId));
        TestCase saved = testCaseRepository.save(
            new TestCase(problem, request.input(), request.expectedOutput()));
        return new TestCaseView(saved.getId(), saved.getInputData(), saved.getExpectedOutput());
    }

    public record TestCaseView(Long id, String input, String expectedOutput) {}

    public record TestCaseRequest(
        @NotBlank @Size(max = 100_000) String input,
        @NotBlank @Size(max = 100_000) String expectedOutput
    ) {}
}
