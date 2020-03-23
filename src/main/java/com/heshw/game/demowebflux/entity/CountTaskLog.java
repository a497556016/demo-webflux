package com.heshw.game.demowebflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class CountTaskLog {
    @Id
    private String objectId;

    private String date;
    private Date startTime;
    private Date endTime;
    private long duration;
    private Boolean success;
    private String error;
}
