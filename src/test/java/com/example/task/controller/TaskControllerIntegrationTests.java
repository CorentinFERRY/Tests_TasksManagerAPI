package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService; // real TaskService object automatically injected in TaskController instance

    @BeforeEach
    void setUp() {
        new ArrayList<>(taskService.getAllTasks())
                .forEach(task -> taskService.deleteTask(task.getTaskId()));
    }

    @Nested
    class GetAllTasks {
        @Test
        void shouldReturnAllTasks() throws Exception {
            taskService.addTask("Task 1");
            mockMvc.perform(get("/tasks"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].taskDescription").value("Task 1"))
                    .andExpect(jsonPath("$[0].taskId").isNotEmpty())
                    .andExpect(jsonPath("$[0].completed").value(false));
        }
    }

    @Nested
    class AddTask {
        @Test
        void shouldReturnTask() throws Exception {
            mockMvc.perform(post("/tasks")
                    .param("description", "Task 1"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.taskDescription").value("Task 1"))
                    .andExpect(jsonPath("$.taskId").isNotEmpty())
                    .andExpect(jsonPath("$.completed").value(false));
        }
    }

    @Nested
    class CompleteTask {
        @Test
        void shouldCompleteTask() throws Exception {
            Task task = taskService.addTask("Task 1");
            mockMvc.perform(put("/tasks/" + task.getTaskId() + "/complete"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Task completed !"));
            assertTrue(task.isCompleted());
        }

        @Test
        void shouldReturn404WhenTaskNotFound() throws Exception {
            mockMvc.perform(put("/tasks/WrongId/complete"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Task doesn't exist !"));
        }
    }

    @Nested
    class DeleteTask {
        @Test
        void shouldReturnNoContentWhenDeleteTask() throws Exception{
            Task task = taskService.addTask("Task 1");
            mockMvc.perform(delete("/tasks/"+task.getTaskId()))
                    .andExpect(status().isNoContent());
            assertFalse(taskService.deleteTask(task.getTaskId()));
        }

        @Test
        void shouldReturn404WhenTaskNotFound() throws Exception {
            mockMvc.perform(delete("/tasks/WrongId"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Task doesn't exist !"));
        }
    }
}
