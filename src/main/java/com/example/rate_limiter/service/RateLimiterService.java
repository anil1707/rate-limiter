package com.example.rate_limiter.service;

import com.example.rate_limiter.dto.RateLimitResult;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {
    int maxLimit = 5;
    Long windowSeconds = 60l;

    private final StringRedisTemplate redisTemplate;

    public RateLimiterService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

//    0. Increment count
    public RateLimitResult allowRequest(String clientId) {

        String key = "rate_limiter:" + clientId;

//        1. get count
        Long count = redisTemplate.opsForValue().increment(key);

        if(count == null){
            throw  new IllegalStateException("Redis did not return a request count");
        }
        //    2. first request, create new window
        if(count == 1){
            redisTemplate.expire(key, windowSeconds, TimeUnit.SECONDS);
        }

        //    3. max limit exceeded
        long countRemaining = maxLimit - count;
        if(count > maxLimit){
            long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            long retryAfter = ttl < 1 ? 1 : ttl;
            return  new RateLimitResult(false, 0, retryAfter);
        }

        //    4. allow
        return  new RateLimitResult(true, (int) countRemaining, 0);

    }
}
