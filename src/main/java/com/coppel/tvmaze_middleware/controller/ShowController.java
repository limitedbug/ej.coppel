package com.coppel.tvmaze_middleware.controller;

import com.coppel.tvmaze_middleware.model.dto.SearchShowResponse;
import com.coppel.tvmaze_middleware.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.coppel.tvmaze_middleware.model.dto.CommentResponse;
import com.coppel.tvmaze_middleware.model.dto.CreateCommentRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@Validated
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/search")
    public List<SearchShowResponse> search(
            @RequestParam("search_query")
            @NotBlank(message = "search_query is required")
            String searchQuery
    ) {
        return showService.search(searchQuery);
    }

    @GetMapping("/{showId}")
    public Map<String, Object> getShow(
            @PathVariable Integer showId
    ) {
        return showService.getShowWithComments(showId);
    }

    @PostMapping("/{showId}/comments")
    public CommentResponse addComment(
            @PathVariable Integer showId,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        return showService.addComment(showId, request);
    }
}