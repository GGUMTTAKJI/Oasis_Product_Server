package kr.co.oasis.product.entitiy.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
* @Author : ParkJongHa
* @CreateAt: 2024-07-25
* @Desc : 모든 테이블에 포함되는 공통 필드를 모아놓은 엔티티의 상위 객체입니다.
* */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class DefaultAudit {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
