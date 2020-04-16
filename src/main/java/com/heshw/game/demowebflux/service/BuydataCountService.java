package com.heshw.game.demowebflux.service;

import com.heshw.game.demowebflux.entity.Buydata;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.repository.BuyDataRepository;
import com.heshw.game.demowebflux.repository.LevelDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class BuydataCountService {
    @Autowired
    private BuyDataRepository levelDataRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public void updateCount(String beginDate, String endDate) {
        Query query = new Query();
        String formatter = "YYYY-MM-dd HH:mm:ss";
        try {
            Date date1 = DateUtils.parseDate(beginDate, formatter);
            Date date2 = DateUtils.parseDate(endDate, formatter);
            query.addCriteria(Criteria.where("_id").gte(new ObjectId(date1)).lt(new ObjectId(date2)));
        }catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        mongoTemplate.find(query, Buydata.class).buffer().subscribe(buydatas -> {
            if(null != buydatas) {
                buydatas.forEach(buydata -> {

                });
            }
        });
    }
}
