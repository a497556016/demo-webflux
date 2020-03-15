package com.heshw.game.demowebflux.repository;

import com.heshw.game.demowebflux.entity.LevelData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelDataRepository extends ReactiveCrudRepository<LevelData, String> {
}
