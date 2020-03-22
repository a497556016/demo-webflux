package com.heshw.game.demowebflux.service;

import com.heshw.game.demowebflux.entity.LevelDataCount;
import com.heshw.game.demowebflux.bean.PageResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LevelDataService {
    public Mono<PageResult<LevelDataCount>> findCountByPage(int pageSize, int pageNum, String beginDate, String endDate, String version) {
        final Query query = new Query();
        return Mono.create(pageResultMonoSink -> {

            pageResultMonoSink.success();
        });
    }
}
