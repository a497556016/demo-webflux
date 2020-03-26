package com.heshw.game.demowebflux.controller;

import com.heshw.game.demowebflux.bean.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private LoginUser loginUser;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser, HttpServletRequest request){
        if(this.loginUser.getUsername().equals(loginUser.getUsername())
        &&this.loginUser.getPassword().equals(loginUser.getPassword())) {
            request.getSession().setAttribute("loginUser", loginUser);
            return ResponseEntity.ok("success");
        }else {
            return ResponseEntity.ok("error");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("loginUser");
        try {
            response.sendRedirect("/pages/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
