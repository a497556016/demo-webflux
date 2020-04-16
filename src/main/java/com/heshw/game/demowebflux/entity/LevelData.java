package com.heshw.game.demowebflux.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
/*
建立索引，提高查询速度 */
@CompoundIndexes(
        {
                @CompoundIndex(name = "levelId", def = "{'levelId': 1}"),
                @CompoundIndex(name = "version", def = "{'version': -1}"),
                @CompoundIndex(name = "levelId_version", def = "{'levelId': 1, 'version': -1}")
        }
)
@Data
public class LevelData {
    @Id
    private String objectId;
//    private User user;
    private String userId;
    private int levelId;
    private boolean winOrflase;
    private int gameTime;
    private int showVideoStep;
    private int showVideoItem;
    private int showVideoRelive;
    private int useItem1;
    private int useItem2;
    private int useItem3;
    private int buyStep;
    private int buyItem1;
    private int buyItem2;
    private int buyItem3;
    private int shopBuyNum;
    private String version;
    private int surplusStep;
    private int surplusTarget;
    private int surplusTargetTotal;
    private Date createDate;
    private String channel;
    private String gameName;
}
