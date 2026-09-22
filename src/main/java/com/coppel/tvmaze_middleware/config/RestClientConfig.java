package com.coppel.tvmaze_middleware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String TV_MAZE_BASE_URL =
            "https://api.tvmaze.com";

    @Bean
    public RestClient tvMazeRestClient() {
        return RestClient.builder()
                .baseUrl(TV_MAZE_BASE_URL)
                .build();
    }
}