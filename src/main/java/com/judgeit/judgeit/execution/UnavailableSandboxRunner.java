package com.judgeit.judgeit.execution;

import org.springframework.stereotype.Component;

/**
 * Safe default: never executes submitted code inside the Spring application process.
 * Replace this adapter with a containerized/remote sandbox implementation.
 */
@Component
public class UnavailableSandboxRunner implements SandboxRunner {
    @Override
    public RunResult run(String language, String sourceCode, String stdin) {
        throw new SandboxUnavailableException();
    }
}
