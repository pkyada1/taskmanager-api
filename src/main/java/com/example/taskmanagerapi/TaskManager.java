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


    public boolean removeTaskByNumber(int taskNumber) {

        List<Task> tasks = getTasks();
        int index = taskNumber - 1;

        if (index >= 0 && index < tasks.size()) {

            repository.delete(tasks.get(index));
            return true;
        }

        return false;
    }
}


