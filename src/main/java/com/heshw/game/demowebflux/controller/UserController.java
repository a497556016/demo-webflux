package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.entity.User;
import com.heshw.game.demowebflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public Mono<?> save(@RequestBody User user){
        user.setCreateDate(new Date());
        return userRepository.save(user);
    }
}
