package com.heshw.game.demowebflux.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@CompoundIndexes(
        {
                @CompoundIndex(name = "id", def = "{'id': 1}")
        }
)
public class User {
    @Id
    private String objectId;

    private String id;

    private Date createDate;

}
