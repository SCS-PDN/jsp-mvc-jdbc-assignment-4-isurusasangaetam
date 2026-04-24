package com.university.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.LocalDateTime;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        if (uri.contains("/login") || uri.contains("/register")) {
            System.out.println("[" + LocalDateTime.now() + "] Action Attempted on URI: " + uri);
        }
        
        return true;
    }
}