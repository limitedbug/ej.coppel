package com.coppel.tvmaze_middleware.service;

import com.coppel.tvmaze_middleware.model.dto.SearchShowResponse;
import com.coppel.tvmaze_middleware.model.tvmaze.TvMazeShow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.coppel.tvmaze_middleware.model.mongo.ShowCache;
import com.coppel.tvmaze_middleware.repository.ShowCacheRepository;

import java.time.Instant;

@Service
public class ShowService {

    private final TvMazeClient tvMazeClient;
    private final ShowCacheRepository showCacheRepository;
    public ShowService(TvMazeClient tvMazeClient, ShowCacheRepository showCacheRepository) {
        this.tvMazeClient = tvMazeClient;
        this.showCacheRepository = showCacheRepository;
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

        return showCacheRepository.findById(showId)
                .map(ShowCache::data)
                .orElseGet(() -> fetchAndCacheShow(showId));
    }

    private Map<String, Object> fetchAndCacheShow(Integer showId) {

        Map<String, Object> show = tvMazeClient.getShow(showId);

        ShowCache cache = new ShowCache(
                showId,
                show,
                Instant.now()
        );

        showCacheRepository.save(cache);

        return show;
    }
}