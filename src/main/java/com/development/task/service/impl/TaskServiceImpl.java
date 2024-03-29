package com.development.task.service.impl;

import com.development.task.domain.exception.ResourceNotfoundException;
import com.development.task.domain.task.Task;
import com.development.task.repository.TaskRepository;
import com.development.task.service.ImageService;
import com.development.task.service.TaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ImageService imageService;
    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotfoundException("Task not found"));
    }

    @Override
    public List<Task> getAllByUserId(Long id) {
        return null;
    }

    @Override
    public List<Task> getAllSoonTasks(Duration duration) {
        return null;
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Task create(Task task, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
