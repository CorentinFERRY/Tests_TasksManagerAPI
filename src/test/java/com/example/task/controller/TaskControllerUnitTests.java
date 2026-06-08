package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerUnitTests {
    @Autowired
    private MockMvc mockMvc; //Object used to make HTTP request on our API

    @MockitoBean
    private TaskService taskService; //Mock object TaskService automatically injected in TaskController instance

    @Test
    void hello_should_return_message() throws Exception {
        mockMvc.perform(get("/tasks/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Task Manager API!"));
    }

    @Test
    void shouldReturnAllTasks() throws Exception {

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1"));
        tasks.add(new Task("Task 2"));

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskDescription").value("Task 1"))
                .andExpect(jsonPath("$[0].taskId").isNotEmpty())
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].taskDescription").value("Task 2"));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void shouldReturnTask() throws Exception {
        Task task = new Task("Task 1");
        when(taskService.addTask(task.getTaskDescription())).thenReturn(task);

        mockMvc.perform(post("/tasks")
                .param("description", "Task 1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.taskDescription").value("Task 1"))
                .andExpect(jsonPath("$.taskId").isNotEmpty())
                .andExpect(jsonPath("$.completed").value(false));
    }
}
