package com.heshw.game.demowebflux.controller;

import com.google.gson.Gson;
import com.heshw.game.demowebflux.bean.PageResult;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.repository.LevelDataRepository;
import com.heshw.game.demowebflux.utils.MongoPageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/levelData")
@Slf4j
public class LevelDataController {

    @Autowired
    private LevelDataRepository levelDataRepository;

    @Autowired
    private MongoPageHelper pageHelper;

    @PostMapping
    public Mono<LevelData> save(HttpServletRequest request){
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer();
        try {
            reader = request.getReader();
            String str = null;
            while ((str = reader.readLine()) != null) {
                data.append(str);
            }
            reader.close();
        }catch (IOException e) {
            log.error(e.getMessage());
        }finally {
            if(null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        GsonJsonParser parser = new GsonJsonParser();
        Map<String, Object> map = parser.parseMap(data.toString());
        LevelData levelData = new LevelData();
        levelData.setId(Objects.toString(map.get("id"), null));
        levelData.setLevelId(Objects.toString(map.get("levelID"), null));
        levelData.setType(Objects.toString(map.get("type"), null));
        Object isMain = map.get("isMain");
        if(isMain instanceof Boolean) {
            levelData.setIsMain((Boolean) isMain);
        }
        return this.levelDataRepository.save(levelData);
    }

    @PostMapping("/params")
    public Mono<LevelData> save1(LevelData levelData){
        return this.levelDataRepository.save(levelData);
    }

    @GetMapping
    public Mono<PageResult<LevelData>> page(int pageSize, int pageNum){
        final Query query = new Query();
        return Mono.create(pageResultFluxSink -> {
            log.info("{}", pageResultFluxSink);
            pageResultFluxSink.success(pageHelper.pageQuery(query, LevelData.class, pageSize, pageNum));
        });
    }

    @GetMapping("/{id}")
    public Mono<LevelData> get(@PathVariable("id") String id){
        return levelDataRepository.findById(id);
    }
}
