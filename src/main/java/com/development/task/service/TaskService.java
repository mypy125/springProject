package com.development.task.service;

import com.development.task.domain.task.Task;

import java.time.Duration;
import java.util.List;

public interface TaskService {
    Task getById(Long id);
    List<Task> getAllByUserId(Long id);
    List<Task> getAllSoonTasks(Duration duration);
    Task update(Task task);
    Task create(Task task, Long id);
    void delete(Long id);
}
