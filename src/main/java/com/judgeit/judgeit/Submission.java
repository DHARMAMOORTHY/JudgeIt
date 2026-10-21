package com.judgeit.judgeit;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please select a problem.")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @NotBlank(message = "Please select a programming language.")
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String language;

    @NotBlank(message = "Source code cannot be empty.")
    @Size(max = 20000, message = "Source code must not exceed 20,000 characters.")
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String sourceCode;

    // PENDING is intentional: this module stores submissions but does not execute code.
    @Column(nullable = false, length = 30)
    private String verdict = "PENDING";

    @Column(length = 2000)
    private String resultDetails = "Submission saved. Automated evaluation is not configured yet.";

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    public Submission() {}

    @PrePersist
    public void beforeInsert() {
        if (submittedAt == null) submittedAt = LocalDateTime.now();
        if (verdict == null || verdict.isBlank()) verdict = "PENDING";
    }

    public Long getId() { return id; }
    public Problem getProblem() { return problem; }
    public void setProblem(Problem problem) { this.problem = problem; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }
    public String getVerdict() { return verdict; }
    public void setVerdict(String verdict) { this.verdict = verdict; }
    public String getResultDetails() { return resultDetails; }
    public void setResultDetails(String resultDetails) { this.resultDetails = resultDetails; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}