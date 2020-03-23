package com.heshw.game.demowebflux.bean;

import com.heshw.game.demowebflux.utils.excel.ExcelField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 统计结果
 */
@Data
public class LevelDataCount {

    @ExcelField(title = "等级")
    private int levelId;
    @ExcelField(title = "进入次数")
    private int playTimes;
    @ExcelField(title = "进入人数")
    private long playUsers;
    @ExcelField(title = "胜利次数")
    private long winTimes;
    @ExcelField(title = "失败次数")
    private long loseTimes;
    @ExcelField(title = "平均时长")
    private int avgDuration;
    @ExcelField(title = "观看视频步数")
    private int videoSteps;
    @ExcelField(title = "观看视频人数")
    private long videoUsers;
    /*@ExcelField(title = "视频购买复活次数")
    private int videoReliveTimes;
    @ExcelField(title = "视频购买复活人数数")
    private long videoReliveUsers;*/
    @ExcelField(title = "视频道具次数")
    private int videoItemCount;
    @ExcelField(title = "视频道具使用人数")
    private long videoItemUsers;
    @ExcelField(title = "道具1使用次数")
    private int item1UseTimes;
    @ExcelField(title = "道具1使用人数")
    private long item1UseUsers;
    @ExcelField(title = "道具2使用次数")
    private int item2UseTimes;
    @ExcelField(title = "道具2使用人数")
    private long item2UseUsers;
    @ExcelField(title = "道具3使用次数")
    private int item3UseTimes;
    @ExcelField(title = "道具3使用人数")
    private long item3UseUsers;
    @ExcelField(title = "复活界面步数+5使用次数")
    private int buyStepTimes;
    @ExcelField(title = "步数+5使用人数")
    private long buyStepUsers;
    @ExcelField(title = "道具1购买次数")
    private int buyItem1Times;
    @ExcelField(title = "道具1购买人数")
    private long buyItem1Users;
    @ExcelField(title = "道具2购买次数")
    private int buyItem2Times;
    @ExcelField(title = "道具2购买人数")
    private long buyItem2Users;
    @ExcelField(title = "道具3购买次数")
    private int buyItem3Times;
    @ExcelField(title = "道具3购买人数")
    private long buyItem3Users;
    @ExcelField(title = "商店购买人数")
    private long shopBuyUsers;//购买人数
    @ExcelField(title = "商店购买次数")
    private int shopBuyTimes;//购买次数
    @ExcelField(title = "消费人数")
    private long payUsers;//消费人数
}
