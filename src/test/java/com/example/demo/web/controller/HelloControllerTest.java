package com.example.demo.web.controller;

import com.example.demo.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// @WebMvcTest(controllers = HelloController.class)

/**
 * 5.7 기존 테스트에 시큐리티 적용하기 p.220
 * ■ Security Test 적용 문제 3. @WebMvcTest에서 CustomOAuth2UserService 을 찾을 수 없음
 * 에러 메시지: No qualifying bean of type 'com.example.demo.config.auth.CustomOAuth2UserService'
 * 문제 1번과 동일한 메시지이다.
 *
 * - @WebMvcTest는 CustomOAuth2UserService를 스캔하지 않는다.
 * @WebMvcTest는 WebSecurityConfigurrAdapter, WebMvcConfigurer를 비롯한
 * @ControllerAdvice, @Controller를 읽는다.
 * 즉, @Repository, @Service, @Conponent는 스캔 대상이 아니다.
 * 그러니 SecurityConfig는 읽었지만, SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService는
 * 읽을수가 없어서 에러가 발생한다. 이 문제를 해결하기 위해 스캔 대상에서 SecurityConfig를 제거한다.
 *
 * @WebMvcTest의 secure 옵션은 2.1부터 Deprecated 되었고, 언제 삭제될지 모르니 사용하지 않은걸 추천한다.
 * 여기서도 마찬가지로 @WithMockUser를 사용해서 가짜로 인증된 사용자를 생성한다.
 */
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * 5.7 기존 테스트에 시큐리티 적용하기 p.213
     * ■ Security Test 적용 문제 1. CustomOAuth2UserService를 찾을 수 없음
     * 에러 메시지: No qualifying bean of type 'com.example.demo.config.auth.CustomOAuth2UserService'
     *
     * CustomOAuth2UserService를 생성하는데 필요한 소셜 로그인 관련 설정값들이 없기 때문에 발생한다.
     * test/resources/application.properties 에 실제로 구글 연동까지 진행할 것은 아니므로
     * 가짜 설정값을 등록한다.
     */
    @Test
    @WithMockUser(roles="USER")
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles="USER")
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                    get("/hello/dto")
                            .param("name", name)
                            .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
