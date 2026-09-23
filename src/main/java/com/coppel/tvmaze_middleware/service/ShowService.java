package com.coppel.tvmaze_middleware.service;

import com.coppel.tvmaze_middleware.model.dto.CommentDto;
import com.coppel.tvmaze_middleware.model.dto.SearchShowResponse;
import com.coppel.tvmaze_middleware.model.tvmaze.TvMazeShow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.coppel.tvmaze_middleware.model.mongo.ShowCache;
import com.coppel.tvmaze_middleware.repository.ShowCacheRepository;

import java.time.Instant;
import com.coppel.tvmaze_middleware.model.dto.CommentResponse;
import com.coppel.tvmaze_middleware.model.dto.CreateCommentRequest;
import com.coppel.tvmaze_middleware.model.mongo.Comment;
import com.coppel.tvmaze_middleware.model.mongo.ShowComments;
import com.coppel.tvmaze_middleware.repository.ShowCommentsRepository;

@Service
public class ShowService {

    private final TvMazeClient tvMazeClient;
    private final ShowCacheRepository showCacheRepository;
    private final ShowCommentsRepository showCommentsRepository;
    public ShowService(TvMazeClient tvMazeClient, ShowCacheRepository showCacheRepository,ShowCommentsRepository showCommentsRepository) {
        this.tvMazeClient = tvMazeClient;
        this.showCacheRepository = showCacheRepository;
        this.showCommentsRepository = showCommentsRepository;
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
                show.genres(),
                getComments(show.id())
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

    public CommentResponse addComment(
            Integer showId,
            CreateCommentRequest request
    ) {

        getShow(showId);

        ShowComments showComments = showCommentsRepository
                .findById(showId)
                .orElseGet(() -> new ShowComments(
                        showId,
                        new java.util.ArrayList<>()
                ));

        showComments.addComment(
                new Comment(
                        request.comment(),
                        request.rating()
                )
        );

        showCommentsRepository.save(showComments);

        return new CommentResponse(
                "Comment saved successfully"
        );
    }
    private List<CommentDto> getComments(Integer showId) {

        return showCommentsRepository.findById(showId)
                .map(showComments -> showComments.getComments()
                        .stream()
                        .map(comment -> new CommentDto(
                                comment.comment(),
                                comment.rating()
                        ))
                        .toList()
                )
                .orElseGet(List::of);
    }

    public Map<String, Object> getShowWithComments(Integer showId) {

        Map<String, Object> show = getShow(showId);

        Map<String, Object> response =
                new java.util.LinkedHashMap<>(show);

        response.put("comments", getComments(showId));

        return response;
    }
}