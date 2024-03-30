package com.development.task.service.impl;

import com.development.task.domain.exception.ResourceNotfoundException;
import com.development.task.domain.task.Status;
import com.development.task.domain.task.Task;
import com.development.task.domain.task.TaskImage;
import com.development.task.repository.TaskRepository;
import com.development.task.service.ImageService;
import com.development.task.service.TaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ImageService imageService;
    @Override
    @Cacheable(value = "TaskService::getById", key = "#id")
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotfoundException("Task not found"));
    }

    @Override
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    public List<Task> getAllSoonTasks(Duration duration) {
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.findAllSoonTasks(Timestamp.valueOf(now),Timestamp.valueOf(now.plus(duration)));
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getById", key = "#task.id")
    public Task update(Task task) {
        Task exist = getById(task.getId());
        if(task.getStatus() == null){
            exist.setStatus(Status.TODO);
        }else{
            exist.setStatus(task.getStatus());
        }
        exist.setTitle(task.getTitle());
        exist.setDescription(task.getDescription());
        exist.setExpirationDate(task.getExpirationDate());
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional
    @Cacheable(value = "TaskService::getById", condition = "#task.id!=null", key = "#task.id")
    public Task create(Task task, Long uid) {
        if(task.getStatus() != null){
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        taskRepository.assignTask(uid, task.getId());
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void uploadImage(Long id, TaskImage image) {
        String fileName = imageService.upload(image);
        taskRepository.addImage(id,fileName);
    }


}
