package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing을 사용하기 위해선 최소 하나의 @Entity 클래스가 필요하다.
 * @WebMvcTest는 일반적인 @Configuration은 스캔하지 않는다.
 */
@Configuration
@EnableJpaAuditing // JPA Auditing 활성화
public class JpaConfig {}
