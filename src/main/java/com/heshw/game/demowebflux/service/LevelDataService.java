package com.heshw.game.demowebflux.service;

import com.google.common.collect.Lists;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.bean.LevelDataCount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LevelDataService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Mono<List<LevelDataCount>> findCountByPage(String beginDate, String endDate, String version, String userCreateDate) {
        final Query query = new Query();
        final Criteria criteria = new Criteria();
        final String dateFormat = "YYYY-MM-dd HH:mm:ss";
        return Mono.create(pageResultMonoSink -> {
            try {
                Date date1 = DateUtils.parseDate(beginDate+" 00:00:00", dateFormat);
                Date date2 = DateUtils.parseDate(endDate+" 23:59:59", dateFormat);
                criteria.and("createDate").gte(date1).lte(date2);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            if(StringUtils.isNotEmpty(version)) {
                criteria.and("version").is(version);
            }
            if(StringUtils.isNotEmpty(userCreateDate)) {
                try {
                    Date date1 = DateUtils.parseDate(userCreateDate+" 00:00:00", dateFormat);
                    Date date2 = DateUtils.parseDate(userCreateDate+" 23:59:59", dateFormat);

                    criteria.and("user.createDate").gte(date1).lte(date2);
                }catch (ParseException e){
                    log.error(e.getMessage());
                }
            }

            query.addCriteria(criteria);
            log.info(query.toString());

            List<LevelData> list = mongoTemplate.find(query, LevelData.class);

            List<LevelDataCount> counts = this.groupCount(list);

            pageResultMonoSink.success(counts);
        });
    }

    private List<LevelDataCount> groupCount(List<LevelData> list) {
        List<LevelDataCount> counts = Lists.newArrayList();

        this.makeCount(counts, list);

        return counts;
    }

    private void makeCount(List<LevelDataCount> counts, List<LevelData> levelDataList) {
        Map<Integer, List<LevelData>> grouped = levelDataList.stream().collect(Collectors.groupingBy(LevelData::getLevelId));
        grouped.forEach((levelId, data) -> {
            LevelDataCount count = new LevelDataCount();
            count.setLevelId(levelId);

            int playTimes = data.size();
            count.setPlayTimes(playTimes);

            long playUsers = data.stream().map(LevelData::getUserId).distinct().count();
            count.setPlayUsers(playUsers);

            long winTimes = data.stream().map(LevelData::getWinOrflase).filter(b -> null != b && b).count();
            count.setWinTimes(winTimes);
            count.setLoseTimes(playTimes - winTimes);

            int avgDuration = data.stream().mapToInt(LevelData::getGameTime).sum()/playTimes;
            count.setAvgDuration(avgDuration);

            int videoSteps = data.stream().mapToInt(LevelData::getShowVideoStep).sum();
            count.setVideoSteps(videoSteps);

            long videoUsers = data.stream().mapToInt(LevelData::getShowVideoStep).filter(s -> s > 0).count();
            count.setVideoUsers(videoUsers);

            int videoItemCount = data.stream().mapToInt(LevelData::getShowVideoItem).sum();
            count.setVideoItemCount(videoItemCount);

            long videoItemUsers = data.stream().mapToInt(LevelData::getShowVideoItem).filter(s -> s > 0).count();
            count.setVideoItemUsers(videoItemUsers);

            int item1UseTimes = data.stream().mapToInt(LevelData::getUseItem1).sum();
            count.setItem1UseTimes(item1UseTimes);

            long item1UseUsers = data.stream().mapToInt(LevelData::getUseItem1).filter(s -> s >0).count();
            count.setItem1UseUsers(item1UseUsers);

            int item2UseTimes = data.stream().mapToInt(LevelData::getUseItem2).sum();
            count.setItem2UseTimes(item2UseTimes);

            long item2UseUsers = data.stream().mapToInt(LevelData::getUseItem2).filter(s -> s >0).count();
            count.setItem2UseUsers(item2UseUsers);

            int item3UseTimes = data.stream().mapToInt(LevelData::getUseItem3).sum();
            count.setItem3UseTimes(item3UseTimes);

            long item3UseUsers = data.stream().mapToInt(LevelData::getUseItem3).filter(s -> s >0).count();
            count.setItem3UseUsers(item3UseUsers);

            int buyStepTimes = data.stream().mapToInt(LevelData::getBuyStep).sum();
            count.setBuyStepTimes(buyStepTimes);

            long buyStepUsers = data.stream().mapToInt(LevelData::getBuyStep).filter(s -> s > 0).count();
            count.setBuyStepUsers(buyStepUsers);

            int buyItem1Times = data.stream().mapToInt(LevelData::getBuyItem1).sum();
            count.setBuyItem1Times(buyItem1Times);

            long buyItem1Users = data.stream().mapToInt(LevelData::getBuyItem1).filter(s -> s > 0).count();
            count.setBuyItem1Users(buyItem1Users);

            int buyItem2Times = data.stream().mapToInt(LevelData::getBuyItem2).sum();
            count.setBuyItem2Times(buyItem2Times);

            long buyItem2Users = data.stream().mapToInt(LevelData::getBuyItem2).filter(s -> s > 0).count();
            count.setBuyItem2Users(buyItem2Users);

            int buyItem3Times = data.stream().mapToInt(LevelData::getBuyItem3).sum();
            count.setBuyItem3Times(buyItem3Times);

            long buyItem3Users = data.stream().mapToInt(LevelData::getBuyItem3).filter(s -> s > 0).count();
            count.setBuyItem3Users(buyItem3Users);

            int shopBuyTimes = data.stream().mapToInt(LevelData::getShopBuyNum).sum();
            count.setShopBuyTimes(shopBuyTimes);

            long shopBuyUsers = data.stream().mapToInt(LevelData::getShopBuyNum).filter(s -> s > 0).count();
            count.setShopBuyUsers(shopBuyUsers);

            long payTimes = videoItemUsers + buyItem1Users + buyItem2Users + buyItem3Users;
            count.setPayTimes(payTimes);

            counts.add(count);
        });
    }
}
