package com.taskboard.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDto(@NotBlank String name, @NotBlank String description) {
}
