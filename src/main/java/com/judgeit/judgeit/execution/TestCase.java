package com.judgeit.judgeit.execution;

import com.judgeit.judgeit.Problem;
import jakarta.persistence.*;

@Entity
@Table(name = "test_cases", indexes = @Index(name = "idx_test_cases_problem_id", columnList = "problem_id"))
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Lob
    @Column(name = "input_data", nullable = false)
    private String inputData;

    @Lob
    @Column(name = "expected_output", nullable = false)
    private String expectedOutput;

    protected TestCase() {}

    public TestCase(Problem problem, String inputData, String expectedOutput) {
        this.problem = problem;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
    }

    public Long getId() { return id; }
    public Problem getProblem() { return problem; }
    public String getInputData() { return inputData; }
    public String getExpectedOutput() { return expectedOutput; }
}
