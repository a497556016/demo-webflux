package com.heshw.game.demowebflux.repository;

import com.heshw.game.demowebflux.entity.Buydata;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyDataRepository extends ReactiveCrudRepository<Buydata, String> {

}
