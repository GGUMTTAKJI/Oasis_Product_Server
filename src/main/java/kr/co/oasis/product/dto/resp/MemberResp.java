package kr.co.oasis.product.dto.resp;

import kr.co.oasis.product.security.jwt.TokenDto;

public record MemberResp() {

    public record login(String status, String message, TokenDto token) {}

}
