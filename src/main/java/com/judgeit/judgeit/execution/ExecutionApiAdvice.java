package com.judgeit.judgeit.execution;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ExecutionApiAdvice {
    @ExceptionHandler(SandboxUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> sandboxUnavailable(SandboxUnavailableException exception) {
        return Map.of("error", "SANDBOX_UNAVAILABLE", "message", exception.getMessage());
    }
}
