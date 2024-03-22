package com.development.task.web.dto.task;

import com.development.task.domain.task.Status;
import com.development.task.web.dto.vaqlidation.OnCreate;
import com.development.task.web.dto.vaqlidation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
@Data
@Schema(description = "Task Dto")
public class TaskDto {
    @NotNull(message = "id most be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "title most be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "title length most be smale then 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 255, message = "description length most be smale then 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String description;
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH-nn")
    private LocalDateTime expirationTime;
}
