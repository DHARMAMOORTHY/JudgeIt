
package com.judgeit.judgeit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Problem title is required.")
    @Size(max = 150, message = "Title must not exceed 150 characters.")
    private String title;

    @NotBlank(message = "Please select a difficulty.")
    private String difficulty;

    @NotBlank(message = "Problem description is required.")
    @Size(max = 5000, message = "Description must not exceed 5000 characters.")
    private String description;

    public Problem() {
    }

    public Problem(String title, String difficulty, String description) {
        this.title = title;
        this.difficulty = difficulty;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
