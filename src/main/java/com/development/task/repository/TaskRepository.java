package com.development.task.repository;

import com.development.task.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = """
            SELECT * FROM tasks t
            JOIN users_tasks ut ON ut.task_id = t.id
            WHERE ut.user_id = :userId
            """, nativeQuery = true)
    List<Task> findAllByUserId(@Param("userId") Long userId);
    @Query(value = """
            SELECT * FROM tasks t
            WHERE t.expiration_date is not null
            AND t.expiration_date between :start and :end
            """, nativeQuery = true)
    List<Task>findAllSoonTasks(@Param("start")Timestamp start, @Param("end")Timestamp end);

    @Modifying
    @Query(value = """
            INSERT INTO users_tasks (user_id, task_id)
            VALUES (:userId, :taskId)
            """, nativeQuery = true)
    void assignTask(@Param("taskId") Long taskId,@Param("userId") Long userId);

    @Modifying
    @Query(value = """
            INSERT INTO tasks_images (task_id, image)
            VALUES (:id, :fileName)
            """, nativeQuery = true)
    void addImage(@Param("id")Long id,@Param("fileName")String fileName);

}
