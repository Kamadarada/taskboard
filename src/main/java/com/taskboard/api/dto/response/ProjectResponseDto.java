package com.taskboard.api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponseDto(UUID id, String name, String description, Integer tasksCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
