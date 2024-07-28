package com.opentext.sample.config;

import com.opentext.sample.model.Result;
import com.opentext.sample.ratelimitter.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class CalculatorRequestInterceptor implements HandlerInterceptor {

//    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final long tokensToConsumeByDefault = 1;
    private int capacity = 2;
    private long tokens = 10;

    @Autowired
    RateLimiter maxLimiter = new RateLimiter(capacity,tokens);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Pre-handling request: " + request.getRequestURI());

        String clientIP = getClientIP(request);
        System.out.println("Client IP "+clientIP);


        if(maxLimiter.isAllowed(clientIP)) {
            System.out.println("Is allowed fro ip "+clientIP);
            return true;
        }
        System.out.println("Is not allowed fro ip "+clientIP);


        Result result  = new Result("a","b", "oper","0.0","max requested");



        // Add custom headers, authentication checks, etc.
        return false; // Continue processing the request
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Post-handling request: " + request.getRequestURI());
        // Modify response data, logging, etc.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Request completed: " + request.getRequestURI());
        // Error handling, cleanup, etc.
    }


    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

}



