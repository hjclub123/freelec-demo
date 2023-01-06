package com.example.demo.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Posts 클래스로 데이터베이스를 접근하게 해줄 JpaRepository를 생성한다. MyBatis 등에서 Dao라고
 * 불리는 DB Layer 접근자이다.
 * 단순히 인터페이스를 생성 후, JpaRepository<Entity 클래스, PK 타입>를 상속하면
 * 기본적인 CRUD 메소드가 자동으로 생성된다.
 * Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.
 *
 * 나중에 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면 이때 Entity 클래스와
 * 기본 Repository는 함께 움직여야 하므로 도메인 패키지에서 함께 관리한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    /**
     * SpringDataJpa에서 제공하는 기본메소드만으로 해결이 가능하지만
     * 제공하지 않는 메소드는 @Query를 사용한다.
     * @query가 훨씬 가독서잉 좋으니 선택해서 사용하면 된다.
     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();


}
