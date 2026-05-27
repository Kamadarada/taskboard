package com.taskboard.api.utils.mappers;

import com.taskboard.api.database.entity.project.ProjectEntity;
import com.taskboard.api.dto.request.ProjectRequestDto;
import com.taskboard.api.dto.response.ProjectResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectMapper {
    public ProjectResponseDto toResponse(ProjectEntity projectEntity) {

        int totalTasks = Optional.ofNullable(projectEntity.getTasks())
                .map(List::size)
                .orElse(0);

        return new ProjectResponseDto(projectEntity.getId(), projectEntity.getName(), projectEntity.getDescription(),
                totalTasks, projectEntity.getCreatedAt(), projectEntity.getUpdatedAt());
    }

    public ProjectEntity toEntity(ProjectRequestDto projectRequestDto) {
        return ProjectEntity.builder().name(projectRequestDto.name()).description(
                projectRequestDto.description()).build();
    }
}
