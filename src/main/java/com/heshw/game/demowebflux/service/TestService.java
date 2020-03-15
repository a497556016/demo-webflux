package com.heshw.game.demowebflux.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestService {
    public String test(){
        try {
            System.out.println("test");
            TimeUnit.MILLISECONDS.sleep(300);
            return "success";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
