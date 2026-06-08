package com.example.task.service;

import com.example.task.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceUnitTest {

    private TaskService taskService;

    @BeforeEach
    void setup() {
        taskService = new TaskService();
    }

    @Nested
    class AddTask {
        @Test
        void addTask_shouldAddTaskToList() {
            taskService.addTask("Test 1");
            assertEquals(1, taskService.getAllTasks().size());
        }

        @Test
        void addTask_shouldKeepDescription() {
            Task test = taskService.addTask("Test 1");
            assertEquals("Test 1", test.getTaskDescription());
        }

        @Test
        void addTask_shouldHaveCompletedFalseByDefault() {
            Task test = taskService.addTask("Test 1");
            assertFalse(test.isCompleted());
        }

        @Test
        void addTask_shouldGenerateNonNullId() {
            Task test = taskService.addTask("Test 1");
            assertNotNull(test.getTaskId());
        }

        @Test
        void addTask_shouldGenerateUniqueId() {
            Task test1 = taskService.addTask("Test 1");
            Task test2 = taskService.addTask("Test 2");
            assertNotEquals(test1.getTaskId(), test2.getTaskId());
        }
    }

    @Nested
    class DeleteTask{
        @Test
        void deleteTask_shouldReturnTrueAndDeleteTask(){
            Task test = taskService.addTask("Test 1");
            assertTrue(taskService.deleteTask(test.getTaskId()));
            assertEquals(0, taskService.getAllTasks().size());
        }

        @Test
        void deleteTask_shouldReturnFalseWhenTaskNotFound(){
            taskService.addTask("Test 1");
            assertFalse(taskService.deleteTask("MauvaisID"));
            assertEquals(1, taskService.getAllTasks().size());
        }
    }

    @Nested
    class CompletedTask{
        @Test
        void completeTask_shouldReturnTrueAndSetCompletedTrue(){
            Task test = taskService.addTask("Test 1");
            assertTrue(taskService.completeTask(test.getTaskId()));
            assertTrue(test.isCompleted());
        }
        @Test
        void completeTask_shouldReturnFalseWhenTaskNotFound(){
            assertFalse(taskService.completeTask("WrongID"));
        }
    }
}
