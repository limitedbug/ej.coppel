package com.coppel.tvmaze_middleware.model.dto;

import java.util.List;

public record SearchShowResponse(
        Integer id,
        String name,
        String channel,
        String summary,
        List<String> genres
) {
}