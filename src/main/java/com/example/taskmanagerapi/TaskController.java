package com.example.taskmanagerapi;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;


@RestController
public class TaskController {

    private final TaskManager manager;

    public TaskController(TaskManager manager) {
        this.manager = manager;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return manager.getTasks();
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody TaskRequest request) {

        Task task = new Task(
            request.title(),
            request.description(),
            request.dueDate()
        );

        manager.addTask(task);

        return task;
    }

    
    @PatchMapping("/tasks/{id}/complete")
    public ResponseEntity<String> completeTask(
        @PathVariable("id") Long id) {

        if (manager.completeTask(id)) {
            return ResponseEntity.ok("Task marked as completed!");
        }

        return ResponseEntity.status(404).body("Task not found.");
    }


    @DeleteMapping("/tasks/{taskNumber}")
    public ResponseEntity<String> deleteTask(
        @PathVariable int taskNumber) {

        if (manager.removeTaskByNumber(taskNumber)) {
            return ResponseEntity.ok("Task successfully deleted!");
        }

        return ResponseEntity.status(404).body("Task not found.");
        }
}



