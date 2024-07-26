package kr.co.oasis.product.entity.domain;

import jakarta.persistence.*;
import kr.co.oasis.product.entity.BaseEntity;
import kr.co.oasis.product.entity.enums.LoginType;
import kr.co.oasis.product.entity.enums.MemberRole;
import kr.co.oasis.product.entity.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "EMAIL_UNIQUE")})
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberStatus memberStatus;

    public Member(String email) {
        this.email = email;
    }


}