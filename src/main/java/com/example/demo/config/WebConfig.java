package com.example.demo.config;

import com.example.demo.config.auth.dto.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 5.4 이노테이션 기반으로 개선하기 p.198
 *
 * LoginUserArgumentResolver가 스프링에서 인식될 수 있도록 WebMvcConfigurer 에 추가한다.
 * HandlerMethodArgumentResolver 는 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해야 한다.
 *
 */
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
