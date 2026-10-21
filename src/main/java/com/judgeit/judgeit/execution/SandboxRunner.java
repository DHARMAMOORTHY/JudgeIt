package com.judgeit.judgeit.execution;

/**
 * Adapter boundary for a separately managed, isolated code-execution sandbox.
 * Implementations must enforce CPU, memory, time, process, filesystem and network limits.
 */
public interface SandboxRunner {
    RunResult run(String language, String sourceCode, String stdin);

    record RunResult(String stdout, String stderr, int exitCode, boolean timedOut) {}
}
