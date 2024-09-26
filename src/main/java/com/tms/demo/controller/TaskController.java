package com.tms.demo.controller;

import com.tms.demo.model.Task;
import com.tms.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/new")
    public String showCreateTaskForm(Model model) {
        Task tasks = new Task();
        model.addAttribute("task", tasks);
        return "task-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        if (optionalTask.isPresent()) {
            model.addAttribute("task", optionalTask.get());
        } else {
            return "redirect:/api/tasks";
        }
        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        if (task.getId() != null) {
            taskService.updateTask(task.getId(), task);
        } else {
            taskService.createTask(task);
        }
        return "redirect:/api/tasks";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/api/tasks";
    }

}
