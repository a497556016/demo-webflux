package com.heshw.game.demowebflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.util.Date;
@CompoundIndexes(
        {
                @CompoundIndex(name = "levelId", def = "{'levelId': 1}"),
                @CompoundIndex(name = "version", def = "{'version': -1}"),
                @CompoundIndex(name = "levelId_version", def = "{'levelId': 1, 'version': -1}")
        }
)
@Data
public class Buydata {

    @Id
    private String objectId;
    //    private User user;
    private String userId;
    private String buyId;
    //private String giftName;
    private int buyNum;
    private String version;
    private Date createDate;
    private String channel;
}
