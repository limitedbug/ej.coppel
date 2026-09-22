package com.coppel.tvmaze_middleware.model.tvmaze;

public record TvMazeSearchResult(
        Double score,
        TvMazeShow show
) {
}