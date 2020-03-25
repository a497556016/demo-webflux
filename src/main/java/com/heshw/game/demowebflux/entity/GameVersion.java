package com.heshw.game.demowebflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class GameVersion {
    @Id
    private String objectId;

    private String version;
}
