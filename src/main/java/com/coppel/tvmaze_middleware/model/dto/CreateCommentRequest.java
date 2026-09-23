package com.coppel.tvmaze_middleware.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentRequest(

        @NotBlank(message = "comment is required")
        String comment,

        @NotNull(message = "rating is required")
        @Min(value = 0, message = "rating must be between 0 and 5")
        @Max(value = 5, message = "rating must be between 0 and 5")
        Integer rating
) {
}