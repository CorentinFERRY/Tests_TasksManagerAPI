package com.example.task.service;

import com.example.task.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final List<Task> taskList = new ArrayList<>();

    public TaskService(){
    }

    public Task addTask(String taskDescription) {
        Task taskToAdd = new Task(taskDescription);
        taskList.add(taskToAdd);
        return taskToAdd;
    }

    public List<Task> getAllTasks() {
        return taskList;
    }
}
