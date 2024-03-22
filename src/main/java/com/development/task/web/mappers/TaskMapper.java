package com.development.task.web.mappers;

import com.development.task.domain.task.Task;
import com.development.task.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto>{

}
