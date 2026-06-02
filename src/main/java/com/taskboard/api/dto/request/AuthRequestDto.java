package com.taskboard.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(@NotBlank String email, @NotBlank String password) {
}
