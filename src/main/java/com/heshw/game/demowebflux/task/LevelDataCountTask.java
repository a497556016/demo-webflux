package com.heshw.game.demowebflux.task;

import com.heshw.game.demowebflux.repository.CountTaskLogRepository;
import com.heshw.game.demowebflux.service.LevelDataCountService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class LevelDataCountTask {
    @Autowired
    private LevelDataCountService levelDataCountService;
    @Autowired
    private CountTaskLogRepository countTaskLogRepository;

//    @Scheduled(cron = "0 0 0 * * ? *")
    public void execute(){
        countTaskLogRepository.findAll(Sort.by(Sort.Direction.DESC, "date")).buffer(1).subscribe(countTaskLogs -> {
            Date date = null;
            String formatter = "YYYY-MM-dd";
            if(null != countTaskLogs && countTaskLogs.size() > 0) {
                String lastDate = countTaskLogs.get(0).getDate();
                try {
                    Date lastDater = DateUtils.parseDate(lastDate, formatter);
                    date = DateUtils.addDays(lastDater, 1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else {
                date = DateUtils.addDays(new Date(), -1);
            }
            if(null != date) {
                String beginDate = DateFormatUtils.format(date, formatter) + " 00:00:00";
                String endDate = DateFormatUtils.format(DateUtils.addDays(date, 1), formatter) + " 00:00:00";
                levelDataCountService.updateCount(beginDate, endDate);
            }
        });
    }
}
