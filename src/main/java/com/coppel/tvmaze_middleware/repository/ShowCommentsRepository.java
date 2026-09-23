package com.coppel.tvmaze_middleware.repository;

import com.coppel.tvmaze_middleware.model.mongo.ShowComments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowCommentsRepository
        extends MongoRepository<ShowComments, Integer> {
}