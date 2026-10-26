package com.example.rate_limiter.dto;

public record ApiResponseDto(
        String message,
        boolean success,
        long status
) {

}
