package com.taskboard.api.dto.response;

import com.taskboard.api.database.entity.task.TaskPriorityEnum;
import com.taskboard.api.database.entity.task.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDto(UUID Id, String name, String description, TaskPriorityEnum priority, TaskStatusEnum status, UUID projectId, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
