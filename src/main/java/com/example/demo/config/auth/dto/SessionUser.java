package com.example.demo.config.auth.dto;

import com.example.demo.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * SessionUser 에는 인증된 사용자 정보만 필요하다.
 *
 * Entity 클래스 User는 직렬화를 하면 안되므로 별도의 클래스를 정의한다.
 * Entity 클래스를 직렬화 하지 않는 이유는 @OneToMany, @ManyToMany 등 자식 엔티티를 갖고 있다면
 * 직렬화 대상에 자식들까지 포함되니 성능 이슈, 부수 효과가 발생할 확률이 높다.
 * 그래서 직력화 기능을 가진 세션 Dto를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 된다.
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
