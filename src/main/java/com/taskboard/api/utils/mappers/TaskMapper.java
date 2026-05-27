package com.taskboard.api.utils.mappers;

import com.taskboard.api.database.entity.project.ProjectEntity;
import com.taskboard.api.database.entity.task.TaskEntity;
import com.taskboard.api.database.repository.IProjectRepository;
import com.taskboard.api.dto.request.TaskRequestDto;
import com.taskboard.api.dto.response.TaskResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final IProjectRepository projectRepository;

    public TaskResponseDto toResponse(TaskEntity taskEntity) {
        return new TaskResponseDto(taskEntity.getId(), taskEntity.getName(), taskEntity.getDescription(), taskEntity.getPriority(), taskEntity.getStatus(), taskEntity.getProject().getId(), taskEntity.getCreatedAt(), taskEntity.getUpdatedAt());
    }

    public TaskEntity toEntity(TaskRequestDto taskRequestDto, ProjectEntity projectEntity) {
        return TaskEntity.builder().name(taskRequestDto.name()).description(taskRequestDto.description()).priority(taskRequestDto.priority()).status(taskRequestDto.status()).project(projectEntity).build();
    }
}
