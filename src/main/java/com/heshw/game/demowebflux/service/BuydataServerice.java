package com.heshw.game.demowebflux.service;

import com.google.common.collect.Lists;
import com.heshw.game.demowebflux.bean.BuydataBean;
import com.heshw.game.demowebflux.bean.LevelDataCount;
import com.heshw.game.demowebflux.entity.Buydata;
import com.heshw.game.demowebflux.entity.LevelData;
import com.heshw.game.demowebflux.entity.User;
import com.heshw.game.demowebflux.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
public class BuydataServerice {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    public Mono<List<BuydataBean>> findCountByPage(String beginDate, String endDate, String version, String userCreateDate,String channel) {
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

            List<Buydata> list = mongoTemplate.find(query, Buydata.class);

            List<BuydataBean> counts = this.groupCount(list);

            pageResultMonoSink.success(counts);
        });
    }

    private List<BuydataBean> groupCount(List<Buydata> list) {
        List<BuydataBean> counts = Lists.newArrayList();

        this.makeCount(counts, list);

        return counts;
    }

    private void makeCount(List<BuydataBean> counts, List<Buydata> levelDataList) {
        Map<String, List<Buydata>> grouped = levelDataList.stream().collect(Collectors.groupingBy(Buydata::getBuyId));
        grouped.forEach((levelId, data) -> {
            BuydataBean count = new BuydataBean();
            count.setBuyName(levelId); //购买ID


            if(levelId.equals("GetCoin")){
                count.setCname("首页激励视频金币礼包");
            }
            else if(levelId.equals("buy1.39")){
                count.setCname("1000金币");
            }
            else if(levelId.equals("buy_remove_ad_2.99")){
                count.setCname("remove ad");
            }
            else if(levelId.equals("buy6.99")){
                count.setCname("2800金币");
            }
            else if(levelId.equals("buy13.99")){
                count.setCname("6000金币");
            }
            else if(levelId.equals("buy39.99")){
                count.setCname("20000金币");
            }
            else if(levelId.equals("buy69.99")){
                count.setCname("40000金币");
            }
            else if(levelId.equals("buy139.99")){
                count.setCname("100000金币");
            }
            else if(levelId.equals("buy11.99")){
                count.setCname("5000金币+礼包");
            }
            else if(levelId.equals("buy_gifts_39.99")){
                count.setCname("15000金币+礼包");
            }

            long buytime = data.stream().map(Buydata::getBuyNum).count();
            count.setBuyNum(buytime);

            long playUsers = data.stream().map(Buydata::getUserId).distinct().count();
            count.setBuyPeopleNum(playUsers);


            counts.add(count);
        });
    }
}
