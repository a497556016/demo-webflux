package com.heshw.game.demowebflux.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "account.user-info")
@Component
public class LoginUser {
    private String username;
    private String password;
}
