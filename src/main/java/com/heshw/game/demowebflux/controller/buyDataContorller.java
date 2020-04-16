package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.bean.BuydataBean;
import com.heshw.game.demowebflux.bean.PageResult;
import com.heshw.game.demowebflux.entity.Buydata;
import com.heshw.game.demowebflux.repository.BuyDataRepository;
import com.heshw.game.demowebflux.repository.UserRepository;
import com.heshw.game.demowebflux.service.BuydataCountService;
import com.heshw.game.demowebflux.service.BuydataServerice;
import com.heshw.game.demowebflux.service.LevelDataCountService;
import com.heshw.game.demowebflux.service.LevelDataService;
import com.heshw.game.demowebflux.utils.MongoPageHelper;
import com.heshw.game.demowebflux.utils.excel.ExportExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buyData")
@Slf4j
public class buyDataContorller {
    @Autowired
    private BuyDataRepository levelDataRepository;

    @Autowired
    private BuydataServerice levelDataService;

    @Autowired
    private BuydataCountService levelDataCountService;

    @Autowired
    private MongoPageHelper pageHelper;

    @Autowired
    private UserRepository userRepository;


    @PostMapping()
    public Mono<?> save(@RequestBody Buydata levelData){
        String userId = levelData.getUserId();
//        double b =  (double)(Math.round(levelData.getSurplusTarget()*10000))/10000;
//        levelData.setSurplusTarget(b);
        if(StringUtils.isNotEmpty(userId)) {
            levelData.setCreateDate(new Date());
            return this.levelDataRepository.save(levelData)/*.doOnSuccess(levelData1 -> {
                poolTaskExecutor.execute(new UpdateUser(userId, levelData1));
            })*/;
        }else {
            return Mono.just("用户ID不能为空！");
        }
    }

    @GetMapping
    public Mono<PageResult<Buydata>> page(@RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(defaultValue = "1") int pageNum,
                                            String beginDate,
                                            String endDate,
                                            String version,
                                            Integer levelId){
        final Query query = new Query();
        return Mono.create(pageResultFluxSink -> {
            log.info("{}", pageResultFluxSink);

            if(null != beginDate || null != endDate) {
                String dateFormat = "YYYY-MM-dd HH:mm:ss";
                Criteria _id = Criteria.where("_id");
                if (null != beginDate) {
                    try {
                        Date date = DateUtils.parseDate(beginDate + " 00:00:00", dateFormat);
                        _id = _id.gte(new ObjectId(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (null != endDate) {
                    try {
                        Date date = DateUtils.parseDate(endDate + " 23:59:59", dateFormat);
                        _id = _id.lte(new ObjectId(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                query.addCriteria(_id);
            }
            if(null != version) {
                Criteria _version = Criteria.where("version").is(version);
                query.addCriteria(_version);
            }
            if(null != levelId) {
                Criteria _levelId = Criteria.where("levelId").is(levelId);
                query.addCriteria(_levelId);
            }
            pageResultFluxSink.success(pageHelper.pageQuery(query, Buydata.class, pageSize, pageNum));
        });
    }

    @GetMapping("/count")
    public Mono<List<BuydataBean>> findCountByPage(String beginDate, String endDate, String version, String userCreateDate,String channel){
        return this.levelDataService.findCountByPage(beginDate, endDate, version, userCreateDate,channel);
    }

    @GetMapping("/export")
    public void export(String beginDate, String endDate, String version, String userCreateDate,String channel, HttpServletResponse response){
        ExportExcel exportExcel = new ExportExcel("导出数据", BuydataBean.class);
        List<BuydataBean> list = this.levelDataService.findCountByPage(beginDate, endDate, version, userCreateDate,channel).block();
        exportExcel.setDataList(list);

        try {
            exportExcel.write(response, "导出数据-"+ UUID.randomUUID().toString()+".xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{id}")
    public Mono<Buydata> get(@PathVariable("id") String id){
        return levelDataRepository.findById(id);
    }

    @PutMapping("/count")
    public Mono<String> updateCount(){
        Date today = new Date();
        String formatter = "YYYY-MM-dd 00:00:00";
        String endDate = DateFormatUtils.format(today, formatter);
        String beginDate = DateFormatUtils.format(DateUtils.addDays(today, -1), formatter);
        return Mono.create(stringMonoSink -> {
            this.levelDataCountService.updateCount(beginDate, endDate);
            stringMonoSink.success("success");
        });
    }
}
