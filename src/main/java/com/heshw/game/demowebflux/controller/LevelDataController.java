package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.bean.PageResult;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.repository.LevelDataRepository;
import com.heshw.game.demowebflux.utils.MongoPageHelper;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/levelData")
@Slf4j
public class LevelDataController {

    @Autowired
    private LevelDataRepository levelDataRepository;

    @Autowired
    private MongoPageHelper pageHelper;

    @PostMapping
    public Mono<LevelData> save(@RequestBody LevelData levelData){
        return this.levelDataRepository.save(levelData);
    }

    @GetMapping
    public Flux<PageResult<LevelData>> page(int pageSize, int pageNum){
        final Query query = new Query();
        return Flux.create(pageResultFluxSink -> {
            log.info("{}", pageResultFluxSink);
            pageResultFluxSink.next(pageHelper.pageQuery(query, LevelData.class, pageSize, pageNum));
            pageResultFluxSink.complete();
        });
    }

    @GetMapping("/{id}")
    public Mono<LevelData> get(@PathVariable("id") String id){
        return levelDataRepository.findById(id);
    }
}
