package com.taskboard.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(@NotBlank String refreshToken) {
}
