package com.heshw.game.demowebflux.bean;

import com.heshw.game.demowebflux.utils.excel.ExcelField;
import lombok.Data;

@Data
public class BuydataBean {
    @ExcelField(title = "名称")
    private String cname;
    @ExcelField(title = "打点名称")
    private String buyName;
    @ExcelField(title = "购买次数")
    private Long buyNum;
    @ExcelField(title = "购买人数")
    private Long buyPeopleNum;

}
