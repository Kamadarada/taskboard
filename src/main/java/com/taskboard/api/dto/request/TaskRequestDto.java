package com.taskboard.api.dto.request;

import com.taskboard.api.database.entity.task.TaskPriorityEnum;
import com.taskboard.api.database.entity.task.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskRequestDto(@NotBlank String name, @NotBlank String description, @NotNull TaskPriorityEnum priority, @NotNull TaskStatusEnum status, @NotNull
                             UUID projectId) {
}
