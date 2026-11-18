package com.example.taskmanagerapi;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskManager {

    private final TaskRepository repository;

    public TaskManager(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(Task task) {
        repository.save(task);
    }

    public List<Task> getTasks() {
        return repository.findAll(Sort.by("id"));
    }

    
    
    public boolean completeTask(Long id) {

        Task task = repository.findById(id).orElse(null);

        if (task == null) {
            return false;
        }

        task.markAsCompleted();
        repository.save(task);

        return true;
    }



    
    public boolean removeTaskById(Long id) {

        Task task = repository.findById(id).orElse(null);

        if (task == null) {
            return false;
        }

        repository.delete(task);

        return true;
    }

}


