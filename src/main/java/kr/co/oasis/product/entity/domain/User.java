package kr.co.oasis.product.entity.domain;

import jakarta.persistence.*;
import kr.co.oasis.product.entity.BaseEntity;
import kr.co.oasis.product.entity.enums.LoginType;
import kr.co.oasis.product.entity.enums.UserRole;
import kr.co.oasis.product.entity.enums.UserStatus;
import lombok.Getter;

@Entity
@Getter
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "EMAIL_UNIQUE")})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus userStatus;

}