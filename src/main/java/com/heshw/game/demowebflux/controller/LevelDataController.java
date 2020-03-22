package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.entity.LevelDataCount;
import com.heshw.game.demowebflux.bean.PageResult;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.repository.LevelDataRepository;
import com.heshw.game.demowebflux.service.LevelDataService;
import com.heshw.game.demowebflux.utils.MongoPageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/levelData")
@Slf4j
public class LevelDataController {

    @Autowired
    private LevelDataRepository levelDataRepository;

    @Autowired
    private LevelDataService levelDataService;

    @Autowired
    private MongoPageHelper pageHelper;

    @PostMapping
    public Mono<LevelData> save(@RequestBody LevelData levelData/*, HttpServletRequest request*/){
        /*BufferedReader reader = null;
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
        }*/
        return this.levelDataRepository.save(levelData);
    }

    @PostMapping("/params")
    public Mono<LevelData> save1(LevelData levelData){
        return this.levelDataRepository.save(levelData);
    }

    @GetMapping
    public Mono<PageResult<LevelData>> page(@RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            String beginDate,
                                            String endDate,
                                            String version,
                                            Integer levelId){
        final Query query = new Query();
        return Mono.create(pageResultFluxSink -> {
            log.info("{}", pageResultFluxSink);
            String dateFormat = "YYYY-MM-dd HH:mm:ss";
            Criteria _id = Criteria.where("_id");
            if(null != beginDate) {
                try {
                    Date date = DateUtils.parseDate(beginDate+" 00:00:00", dateFormat);
                    _id = _id.gte(new ObjectId(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(null != endDate) {
                try {
                    Date date = DateUtils.parseDate(endDate+" 23:59:59", dateFormat);
                    _id = _id.lte(new ObjectId(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            query.addCriteria(_id);
            if(null != version) {
                Criteria _version = Criteria.where("version").is(version);
                query.addCriteria(_version);
            }
            if(null != levelId) {
                Criteria _levelId = Criteria.where("levelId").is(levelId);
                query.addCriteria(_levelId);
            }
            pageResultFluxSink.success(pageHelper.pageQuery(query, LevelData.class, pageSize, pageNum));
        });
    }

    @GetMapping("/count")
    public Mono<PageResult<LevelDataCount>> findCountByPage(@RequestParam(defaultValue = "10") int pageSize,
                                                            @RequestParam(defaultValue = "1") int pageNum,
                                                            String beginDate,
                                                            String endDate,
                                                            String version){
        return this.levelDataService.findCountByPage(pageSize, pageNum, beginDate, endDate, version);
    }

    @GetMapping("/{id}")
    public Mono<LevelData> get(@PathVariable("id") String id){
        return levelDataRepository.findById(id);
    }
}
