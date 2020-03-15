package com.heshw.game.demowebflux.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class LevelData {
    @Id
    private String objectId;
    private String id;
    private String type;
    private String levelId;
    private Boolean isMain;
}
