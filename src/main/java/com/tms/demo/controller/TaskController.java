package com.tms.demo.controller;

import com.tms.demo.model.Task;
import com.tms.demo.service.TaskService;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        log.info("Fetching all Tasks");
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/new")
    public String showCreateTaskForm(Model model) {
        log.info("Displaying Task creation form");
        Task tasks = new Task();
        model.addAttribute("task", tasks);
        return "task-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        log.info("Fetching task with ID: {}", id);
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
            log.info("Task found: {}", optionalTask.get());
        } else {
            log.warn("Task with ID: {} not found", id);
            return "redirect:/api/tasks";
        }
        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        if (task.getId() != null) {
            taskService.updateTask(task.getId(), task);
            log.info("Creating new Task: {}", task);
        } else {
            taskService.createTask(task);
            log.info("Updating Task: {}", task);
        }
        return "redirect:/api/tasks";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        log.info("Deleting Task with ID: {}", id);
        taskService.deleteTask(id);
        return "redirect:/api/tasks";
    }

}
