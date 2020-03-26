package com.heshw.game.demowebflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Configuration
@Order(1)
@Slf4j
public class LoginFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String path = servletRequest.getServletPath();
        String method = servletRequest.getMethod().toLowerCase();
        log.info(path);
        if (!path.startsWith("/lib") && !path.startsWith("/js")
                && !"/pages/login.html".equals(path)
                && !"/account/login".equals(path)
                && !("/user".equals(path)&&"post".equals(method))
                && !("/levelData".equals(path)&&"post".equals(method))
        ) {
            Object o = servletRequest.getSession().getAttribute("loginUser");
            if (null == o) {
                servletResponse.sendRedirect("/pages/login.html");
            }
        }
        super.doFilter(request, response, chain);
    }
}
