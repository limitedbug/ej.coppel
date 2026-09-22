package com.coppel.tvmaze_middleware.controller;

import com.coppel.tvmaze_middleware.model.dto.SearchShowResponse;
import com.coppel.tvmaze_middleware.service.ShowService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}