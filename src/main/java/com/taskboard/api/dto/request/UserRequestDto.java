package com.taskboard.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(@NotBlank String email, @NotBlank String password) {
}
