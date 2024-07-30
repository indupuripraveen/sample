package com.opentext.sample.config;

import com.opentext.sample.model.Result;
import com.opentext.sample.ratelimitter.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class CalculatorRequestInterceptor implements HandlerInterceptor {


    private final int capacity = 3;
    private final long tokens = 10000;
    // private final long tokens = 10;
    RateLimiter rateLimiter = new RateLimiter(capacity, tokens);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Pre-handling request: " + request.getRequestURI());

        String clientIP = getClientIP(request);
        System.out.println("Client IP " + clientIP);

        if (rateLimiter.isAllowed(clientIP)) {
            System.out.println("Is allowed from ip " + clientIP);
            System.out.println("Returning to controller");
            return true;
        }
        System.out.println("Is not allowed from ip " + clientIP);

        Result result = new Result("a", "b", "oper", "0.0", "max requested");
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



