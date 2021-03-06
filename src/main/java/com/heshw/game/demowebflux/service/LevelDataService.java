package com.heshw.game.demowebflux.service;

import com.google.common.collect.Lists;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.bean.LevelDataCount;
import com.heshw.game.demowebflux.entity.User;
import com.heshw.game.demowebflux.repository.UserRepository;
import com.heshw.game.demowebflux.utils.excel.ExcelField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
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

    @Autowired
    private UserRepository userRepository;

    public Mono<List<LevelDataCount>> findCountByPage(String beginDate, String endDate, String version, String userCreateDate,String channel,String game) {
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
            if(StringUtils.isNotEmpty(channel)){
                criteria.and("channel").is(channel);
            }
            if(StringUtils.isNotEmpty(game)){
                if(game.equals("tj")){
                    criteria.and("gameName").nin("ch");
                }else{
                    criteria.and("gameName").is(game);
                }

            }
            if(StringUtils.isNotEmpty(userCreateDate)) {
                try {
                    Date date1 = DateUtils.parseDate(userCreateDate+" 00:00:00", dateFormat);
                    Date date2 = DateUtils.parseDate(userCreateDate+" 23:59:59", dateFormat);
                    Query userQuery = new Query();
                    userQuery.addCriteria(Criteria.where("createDate").gte(date1).lt(date2));
                    List<User> users = mongoTemplate.find(userQuery, User.class);
                    if(!users.isEmpty()) {
                        criteria.and("userId").in(users.stream().map(User::getId).collect(Collectors.toList()));
                    }else {
                        pageResultMonoSink.success(Lists.newArrayList());
                    }
//                    criteria.and("user.createDate").gte(date1).lte(date2);
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

            long winTimes = data.stream().filter(LevelData::isWinOrflase).count();
            count.setWinTimes(winTimes);
            count.setLoseTimes(playTimes - winTimes);

            long winUsers = data.stream().filter(LevelData::isWinOrflase).map(LevelData::getUserId).distinct().count();
            count.setWinUsers(winUsers);
            long loseUsers = data.stream().filter(levelData -> !levelData.isWinOrflase()).map(LevelData::getUserId).distinct().count();
            count.setLoseUsers(loseUsers);

            int avgDuration = data.stream().mapToInt(LevelData::getGameTime).sum()/playTimes;
            count.setAvgDuration(avgDuration);

            int videoSteps = data.stream().mapToInt(LevelData::getShowVideoStep).sum();
            count.setVideoSteps(videoSteps);

            long videoUsers = data.stream().filter(levelData -> levelData.getShowVideoStep() > 0).map(LevelData::getUserId).distinct().count();
            count.setVideoUsers(videoUsers);

            int videoItemCount = data.stream().mapToInt(LevelData::getShowVideoItem).sum();
            count.setVideoItemCount(videoItemCount);

            long videoItemUsers = data.stream().filter(levelData -> levelData.getShowVideoItem() > 0).map(LevelData::getUserId).distinct().count();
            count.setVideoItemUsers(videoItemUsers);

            int item1UseTimes = data.stream().mapToInt(LevelData::getUseItem1).sum();
            count.setItem1UseTimes(item1UseTimes);

            long item1UseUsers = data.stream().filter(levelData -> levelData.getUseItem1() > 0).map(LevelData::getUserId).distinct().count();
            count.setItem1UseUsers(item1UseUsers);

            int item2UseTimes = data.stream().mapToInt(LevelData::getUseItem2).sum();
            count.setItem2UseTimes(item2UseTimes);

            long item2UseUsers = data.stream().filter(levelData -> levelData.getUseItem2() > 0).map(LevelData::getUserId).distinct().count();
            count.setItem2UseUsers(item2UseUsers);

            int item3UseTimes = data.stream().mapToInt(LevelData::getUseItem3).sum();
            count.setItem3UseTimes(item3UseTimes);

            long item3UseUsers = data.stream().filter(levelData -> levelData.getUseItem3() > 0).map(LevelData::getUserId).distinct().count();
            count.setItem3UseUsers(item3UseUsers);

            int buyStepTimes = data.stream().mapToInt(LevelData::getBuyStep).sum();
            count.setBuyStepTimes(buyStepTimes);

            long buyStepUsers = data.stream().filter(levelData -> levelData.getBuyStep() > 0).map(LevelData::getUserId).distinct().count();
            count.setBuyStepUsers(buyStepUsers);

            int buyItem1Times = data.stream().mapToInt(LevelData::getBuyItem1).sum();
            count.setBuyItem1Times(buyItem1Times);

            long buyItem1Users = data.stream().filter(levelData -> levelData.getBuyItem1() > 0).map(LevelData::getUserId).distinct().count();
            count.setBuyItem1Users(buyItem1Users);

            int buyItem2Times = data.stream().mapToInt(LevelData::getBuyItem2).sum();
            count.setBuyItem2Times(buyItem2Times);

            long buyItem2Users = data.stream().filter(levelData -> levelData.getBuyItem2() > 0).map(LevelData::getUserId).distinct().count();
            count.setBuyItem2Users(buyItem2Users);

            int buyItem3Times = data.stream().mapToInt(LevelData::getBuyItem3).sum();
            count.setBuyItem3Times(buyItem3Times);

            long buyItem3Users = data.stream().filter(levelData -> levelData.getBuyItem3() > 0).map(LevelData::getUserId).distinct().count();
            count.setBuyItem3Users(buyItem3Users);

            int shopBuyTimes = data.stream().mapToInt(LevelData::getShopBuyNum).sum();
            count.setShopBuyTimes(shopBuyTimes);

            long shopBuyUsers = data.stream().filter(levelData -> levelData.getShopBuyNum() > 0).map(LevelData::getUserId).distinct().count();
            count.setShopBuyUsers(shopBuyUsers);

            long payUsers = data.stream().filter(levelData ->
                    levelData.getShowVideoItem() > 0
                    ||levelData.getBuyItem1() > 0
                    ||levelData.getBuyItem2() > 0
                    ||levelData.getBuyItem3() > 0)
                    .map(LevelData::getUserId).distinct().count();
            count.setPayUsers(payUsers);

            //
            int avgSurplusStep = data.stream().mapToInt(LevelData::getSurplusStep).sum();
            if(winTimes==0){
                count.setSurplusStep(0);
            }else{
                count.setSurplusStep(avgSurplusStep/winTimes);
            }


            long SurplusTarget = data.stream().mapToInt(LevelData::getSurplusTarget).sum();
            long SurplusTargetTotal = data.stream().mapToInt(LevelData::getSurplusTargetTotal).sum();
            float avgLoseTarget =0.0f;
            if(SurplusTargetTotal != 0) {
                avgLoseTarget =(SurplusTarget *1.0f)   / (SurplusTargetTotal * 1.0f) ;
            }
            DecimalFormat decimalFormat=new DecimalFormat("0.0000");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            String p=decimalFormat.format(avgLoseTarget);
            count.setAvgLoseTarget(p);
           // double surplusTargetcout = data.stream().map(LevelData::getSurplusTarget).sum()/winTimes;

            counts.add(count);
        });
    }
}
