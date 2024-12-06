package com.example.task.model;

import java.util.UUID;

public class Task {
    private String taskId;
    private String taskDescription;
    private boolean completed;

    public Task(String description) {
        this.taskId = UUID.randomUUID().toString();
        this.taskDescription = description;
        this.completed = false;
    }

    // Getters et setters
    public String getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
