package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to the Task Manager API!");
    }

    @GetMapping
    public ResponseEntity<List<Task>> listTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestParam String description){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(description));

    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<?> completeTask(@PathVariable String taskId){
        if(taskService.completeTask(taskId)){
            return ResponseEntity.ok("Task completed !");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task doesn't exist !");
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@PathVariable String taskId) {
        if(taskService.deleteTask(taskId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task doesn't exist !");
    }
}
