package com.coppel.tvmaze_middleware.model.mongo;

public record Comment(
        String comment,
        Integer rating
) {
}