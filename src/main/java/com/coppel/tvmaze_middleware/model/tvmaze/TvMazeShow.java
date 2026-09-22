package com.coppel.tvmaze_middleware.model.tvmaze;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvMazeShow(
        Integer id,
        String name,
        String summary,
        List<String> genres,
        Network network,
        Network webChannel
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Network(
            String name
    ) {
    }
}