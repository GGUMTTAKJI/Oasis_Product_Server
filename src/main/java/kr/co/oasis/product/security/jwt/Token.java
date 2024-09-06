package kr.co.oasis.product.security.jwt;

import kr.co.oasis.product.entity.enums.MemberRole;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Token {

    private String tokenType;
    private String email;
    private String role;
    private Date expireDate;

}
