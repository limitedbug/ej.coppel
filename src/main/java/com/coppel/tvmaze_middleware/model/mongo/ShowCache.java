package com.coppel.tvmaze_middleware.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "shows")
public record ShowCache(
        @Id
        Integer id,

        Map<String, Object> data,

        Instant cachedAt
) {
}