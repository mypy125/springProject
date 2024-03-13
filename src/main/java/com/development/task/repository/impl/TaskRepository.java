package com.development.task.repository.impl;

import com.development.task.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);
    List<Task> findAllByUserId(Long userId);
    void assignToUserById(Long taskId, Long userId);
    void update(Task task);
    void create(Task task);
    void delete(Long id);
}
