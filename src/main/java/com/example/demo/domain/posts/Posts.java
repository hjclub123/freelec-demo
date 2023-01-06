package com.example.demo.domain.posts;

import com.example.demo.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안되고 Dto 클래스를 추가하여 사용한다.
 *
 * 기본적인 구조는 생성자를 통해 최종값을 채운 후 DB에 삽입하는 것이며,
 * 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 한다.
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    /**
     * 생성자 대신에 @Builder를 통해 제공되는 빌더 클래스를 사용한다. 생성자나 빌더나 생성 시점에 값을 채워주는
     * 역할은 똑같다. 하지만, 생성자의 경우 지금 채워야 할 필드가 무엇인지 명확히 지정할 수가 없다.
     * 빌더를 사용하게 되면 어느 필드에 어떤 값을 채워야할지 명확하게 인지할 수 있다.
     *
     * Example.builder()
     *     .a(a)
     *     .b(b)
     *     .build();
     */
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /**
     * 	자바빈 규약을 생각하면서 getter/setter를 무작정 생성하는 경우가 있다.
     * 	이렇게 되면 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지 코드상으로 명확하게 구분할 수가 없어,
     * 	차후 기능 변경 시 정말 복잡해진다.
     * 그래서 Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다.
     * 대신, 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가해야만한다.
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
