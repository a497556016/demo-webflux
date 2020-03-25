package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.bean.PageResult;
import com.heshw.game.demowebflux.entity.GameVersion;
import com.heshw.game.demowebflux.repository.GameVersionRepository;
import com.heshw.game.demowebflux.utils.MongoPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gameVersion")
public class GameVersionController {

    @Autowired
    private GameVersionRepository repository;

    @Autowired
    private MongoPageHelper pageHelper;

    @PostMapping
    public Mono<GameVersion> save(@RequestBody GameVersion version){
        return repository.save(version);
    }

    @GetMapping
    public Flux<GameVersion> list(){
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public Mono<?> delete(@PathVariable("id") String id){
        return repository.deleteById(id);
    }

    @GetMapping("/page")
    public Mono<PageResult<GameVersion>> page(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int pageNum){
        final Query query = new Query();
        return Mono.create(pageResultMonoSink -> {
            pageResultMonoSink.success(pageHelper.pageQuery(query, GameVersion.class, pageSize, pageNum));
        });
    }
}
