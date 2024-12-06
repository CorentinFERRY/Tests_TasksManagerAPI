package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService; // real TaskService object automatically injected in TaskController instance

    @Test
    void shouldReturnAllTasks() throws Exception {

        taskService.addTask("Task 1");

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskDescription").value("Task 1"))
                .andExpect(jsonPath("$[0].taskId").isNotEmpty());
    }
}
