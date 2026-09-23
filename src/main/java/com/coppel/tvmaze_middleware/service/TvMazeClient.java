package com.coppel.tvmaze_middleware.service;

import com.coppel.tvmaze_middleware.model.tvmaze.TvMazeSearchResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class TvMazeClient {

    private final RestClient restClient;

    public TvMazeClient(RestClient tvMazeRestClient) {
        this.restClient = tvMazeRestClient;
    }

    public List<TvMazeSearchResult> searchShows(String query) {

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/shows")
                        .queryParam("q", query)
                        .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Map<String, Object> getShow(Integer showId) {

        return restClient
                .get()
                .uri("/shows/{id}", showId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}