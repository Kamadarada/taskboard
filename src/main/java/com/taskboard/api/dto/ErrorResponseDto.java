package com.taskboard.api.dto;

public record ErrorResponseDto(
        int Status,
        String message
) {
}
