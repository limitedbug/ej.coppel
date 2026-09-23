package com.coppel.tvmaze_middleware.repository;

import com.coppel.tvmaze_middleware.model.mongo.ShowCache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowCacheRepository extends MongoRepository<ShowCache, Integer> {
}