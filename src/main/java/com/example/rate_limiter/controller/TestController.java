package com.example.rate_limiter.controller;

import com.example.rate_limiter.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/")
    public ResponseEntity<ApiResponseDto> home(){
        ApiResponseDto apiResponseDto = new ApiResponseDto("Hello form home", true, 200);
        return ResponseEntity.ok(apiResponseDto);
    }

    @GetMapping("/about")
    public ResponseEntity<ApiResponseDto> about(){
        ApiResponseDto apiResponseDto = new ApiResponseDto("Hello form home", true, 200);
        return ResponseEntity.ok(apiResponseDto);
    }

    @GetMapping("/contact-us")
    public ResponseEntity<ApiResponseDto> contactUs(){
        ApiResponseDto apiResponseDto = new ApiResponseDto("Hello form home", true, 200);
        return ResponseEntity.ok(apiResponseDto);
    }
}
