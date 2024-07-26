package kr.co.oasis.product.dto.resp;

public record MemberResp() {

    public record login(String status, String message, String token) {}

}
