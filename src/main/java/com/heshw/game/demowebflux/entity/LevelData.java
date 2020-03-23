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
    private User user;
    private String userId;
    private Integer levelId;
    private Boolean winOrflase;
    private Integer gameTime;
    private Integer showVideoStep;
    private Integer showVideoItem;
    private Integer showVideoRelive;
    private Integer useItem1;
    private Integer useItem2;
    private Integer useItem3;
    private Integer buyStep;
    private Integer buyItem1;
    private Integer buyItem2;
    private Integer buyItem3;
    private Integer shopBuyNum;
    private String version;

    private Date createDate;
}
