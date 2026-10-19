
package com.judgeit.judgeit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleProblems(ProblemRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Problem(
                    "Two Sum",
                    "Easy",
                    "Given an array of integers and a target, find two numbers whose sum equals the target."
                ));

                repository.save(new Problem(
                    "Reverse a String",
                    "Easy",
                    "Write a program to reverse the given string."
                ));

                repository.save(new Problem(
                    "Binary Search",
                    "Medium",
                    "Given a sorted array, find the index of a target element. Return -1 if it is not found."
                ));

                System.out.println("JudgeIt sample problems inserted successfully.");
            }
        };
    }
}
