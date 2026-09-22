package com.coppel.tvmaze_middleware.service;

import com.coppel.tvmaze_middleware.model.dto.SearchShowResponse;
import com.coppel.tvmaze_middleware.model.tvmaze.TvMazeShow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShowService {

    private final TvMazeClient tvMazeClient;

    public ShowService(TvMazeClient tvMazeClient) {
        this.tvMazeClient = tvMazeClient;
    }

    public List<SearchShowResponse> search(String query) {

        return tvMazeClient.searchShows(query)
                .stream()
                .map(result -> toSearchResponse(result.show()))
                .toList();
    }

    private SearchShowResponse toSearchResponse(TvMazeShow show) {

        return new SearchShowResponse(
                show.id(),
                show.name(),
                resolveChannel(show),
                show.summary(),
                show.genres()
        );
    }

    private String resolveChannel(TvMazeShow show) {

        if (show.network() != null) {
            return show.network().name();
        }

        if (show.webChannel() != null) {
            return show.webChannel().name();
        }

        return null;
    }
    public Map<String, Object> getShow(Integer showId) {
        return tvMazeClient.getShow(showId);
    }
}