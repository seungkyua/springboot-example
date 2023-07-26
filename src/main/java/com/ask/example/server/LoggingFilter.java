package com.ask.example.server;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Order(2)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("=========== Before {} FilterChain", "LoggingFilter");
//        System.out.println("============= Before LoggingFilter");
        chain.doFilter(request, response);
        log.info("=========== After {} FilterChain", "LoggingFilter");
//        System.out.println("============= After LoggingFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
