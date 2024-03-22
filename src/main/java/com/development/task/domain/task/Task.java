package com.development.task.domain.task;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "tasks")
@Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private LocalDateTime expirationTime;

    @Column(name = "image")
    @CollectionTable(name = "task_images")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;
}
