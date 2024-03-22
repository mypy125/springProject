package com.development.task.web.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "Task Image Dto")
public class TaskImageDto {
    @NotNull(message = "Image most be not null.")
    private MultipartFile file;
}
