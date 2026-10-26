package com.example.rate_limiter.filter;

import com.example.rate_limiter.dto.RateLimitResult;
import com.example.rate_limiter.service.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    private RateLimiterService rateLimiterService;

    public RateLimitFilter(RateLimiterService rateLimiterService){
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String clientId = request.getRemoteAddr();
        RateLimitResult result = rateLimiterService.allowRequest(clientId);
        System.out.println("result: ==============> " + result);
        if(!result.valid()){
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("rate-limit", Long.toString(result.retryAfter()));
            response.getWriter().write(
                    """
                       {
                         "message": "Too many request, please try after %ds time,
                         "success": false,
                         "status": 429,
                       }
                       """.formatted(result.retryAfter())
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
