package com.heshw.game.demowebflux.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "account.user-info1")
@Component
public class LoginUserTwo {
    private String username;
    private String password;
}
