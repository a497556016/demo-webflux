package com.heshw.game.demowebflux.repository;

import com.heshw.game.demowebflux.entity.GameVersion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameVersionRepository extends ReactiveMongoRepository<GameVersion, String> {
}
