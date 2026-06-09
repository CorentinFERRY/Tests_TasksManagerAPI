package com.example.task.service;

import com.example.task.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public boolean deleteTask(String id){
        return taskList.removeIf(task -> task.getTaskId().equals(id));
    }

    public boolean completeTask(String id){
        Optional<Task> taskToSetCompleted = taskList.stream()
            .filter(task -> task.getTaskId().equals(id))
            .findFirst();
        taskToSetCompleted.ifPresent(task -> task.setCompleted(true));
        return taskToSetCompleted.isPresent();
    }
}
