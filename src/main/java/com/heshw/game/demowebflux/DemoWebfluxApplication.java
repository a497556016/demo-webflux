package com.heshw.game.demowebflux;

import com.heshw.game.demowebflux.entity.LevelData;
import com.spring4all.mongodb.EnableMongoPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@SpringBootApplication
@EnableMongoPlus
public class DemoWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebfluxApplication.class, args);
    }

}
