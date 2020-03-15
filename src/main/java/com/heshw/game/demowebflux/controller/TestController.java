package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/sayHi")
    public Mono<Object> sayHi(){
        return Mono.just("Hello Webflux!")
                .publishOn(Schedulers.elastic())
                .map(s -> testService.test());
        /*return Mono.create(stringMonoSink -> {
            System.out.println("say hi");
            stringMonoSink.success(testService.test());
        }).doOnSubscribe(subscription -> {
            log.info("{}", subscription);
        }).doOnNext(o -> {
            log.info("{}", o);
        });*/
    }
}
