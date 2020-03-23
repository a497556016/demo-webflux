package com.heshw.game.demowebflux.repository;

import com.heshw.game.demowebflux.entity.CountTaskLog;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountTaskLogRepository extends ReactiveMongoRepository<CountTaskLog, String> {

}
