package com.example.demo.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 모든 Entity의 상위 클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할
 *
 * @MappedSuperclass
 * JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도
 * 칼럼으로 인식하도록 한다.
 *
 * @EntityListeners(AuditingEntityListener.class)
 * 해당 클래스에 Auditing 기능을 포함시킨다.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    /**
     * Entity가 생성되어 저장될 때 시간이 자동 저장된다.
     */
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
