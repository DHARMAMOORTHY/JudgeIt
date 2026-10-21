package com.judgeit.judgeit.execution;

import java.util.List;

public record ExecutionResult(
    Long problemId,
    String verdict,
    int passedTestCases,
    int totalTestCases,
    List<TestCaseResult> testCases,
    String message
) {
    public record TestCaseResult(Long testCaseId, String verdict, String actualOutput) {}
}
