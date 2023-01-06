package com.example.demo.config.auth.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 5.4 어노테이션 기반으로 개선하기
 * 메소드 인자로 세션값을 바로 받을 수 있도록 하기위해 @LoginUser 어노테이션을 생성한다.
 *
 * - @Target(ElementType.PARAMETER)
 * 이 어노테이션이 생성될 수 있는 위치를 지정한다.
 * PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다.
 * 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있다.
 *
 * - @interface
 * 이 파일을 어노테이션 클래스로 지정한다.
 * LoginUser라는 이름을 가진 어노테이션이 생성되었다고 보면 된다.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
