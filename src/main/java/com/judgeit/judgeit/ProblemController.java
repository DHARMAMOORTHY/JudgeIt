
package com.judgeit.judgeit;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

@Controller
public class ProblemController {

    private final ProblemRepository problemRepository;

    public ProblemController(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    // Display all problems
    @GetMapping("/problems")
    public String showProblems(Model model) {
        List<Problem> problems = problemRepository.findAll();
        model.addAttribute("problems", problems);
        return "problems";
    }

    // Show the Add Problem form
    @GetMapping("/problems/new")
    public String showAddProblemForm(Model model) {
        model.addAttribute("problem", new Problem());
        return "add-problem";
    }

    // Validate and save a new problem
    @PostMapping("/problems")
    public String addProblem(
            @Valid @ModelAttribute("problem") Problem problem,
            BindingResult result) {

        if (result.hasErrors()) {
            return "add-problem";
        }

        problemRepository.save(problem);
        return "redirect:/problems";
    }

    // Show the Edit Problem form
    @GetMapping("/problems/edit/{id}")
    public String showEditProblemForm(
            @PathVariable Long id, Model model) {

        Problem problem = problemRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid problem ID: " + id));

        model.addAttribute("problem", problem);
        return "edit-problem";
    }

    // Validate and update an existing problem
    @PostMapping("/problems/update/{id}")
    public String updateProblem(
            @PathVariable Long id,
            @Valid @ModelAttribute("problem") Problem formProblem,
            BindingResult result) {

        if (result.hasErrors()) {
            return "edit-problem";
        }

        Problem existing = problemRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid problem ID: " + id));

        existing.setTitle(formProblem.getTitle());
        existing.setDifficulty(formProblem.getDifficulty());
        existing.setDescription(formProblem.getDescription());

        problemRepository.save(existing);
        return "redirect:/problems";
    }

    // Delete a problem
    @PostMapping("/problems/delete/{id}")
    public String deleteProblem(@PathVariable Long id) {
        if (problemRepository.existsById(id)) {
            problemRepository.deleteById(id);
        }

        return "redirect:/problems";
    }
}
