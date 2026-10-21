package com.judgeit.judgeit.execution;

public class SandboxUnavailableException extends RuntimeException {
    public SandboxUnavailableException() {
        super("Code execution is not configured. Connect a secure sandbox runner before enabling submissions.");
    }
}
