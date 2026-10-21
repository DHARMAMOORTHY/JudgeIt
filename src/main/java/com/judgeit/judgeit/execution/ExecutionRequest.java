package com.judgeit.judgeit.execution;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExecutionRequest(
    @NotNull Long problemId,
    @NotBlank @Size(max = 100_000) String sourceCode,
    @NotBlank @Size(max = 30) String language
) {}
