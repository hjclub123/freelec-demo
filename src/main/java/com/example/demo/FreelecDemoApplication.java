package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ■ 웹 콜솔 사용
 * http://localhost:8080/h2-console
 *
 * select * from posts;
 * insert into posts (author, content, title) values ('au', 'con', 'tit');
 *
 * ■ 브라우저 요청 확인
 * http://localhost:8080/api/v1/posts/1
 *
 * ■ 8080 port 에러시
 * > netstat -ano | findstr 8080
 * > taskkill /F /pid
 *
 * ■ role을 USER로 변경
 * update user set role = 'USER';
 */

@SpringBootApplication
public class FreelecDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelecDemoApplication.class, args);
	}

}
