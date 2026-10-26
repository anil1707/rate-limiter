package com.example.rate_limiter.dto;

public record RateLimitResult(
        boolean valid,
        int limit,
        long retryAfter
) {
}
