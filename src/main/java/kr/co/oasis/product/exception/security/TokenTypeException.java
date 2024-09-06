package kr.co.oasis.product.exception.security;

public class TokenTypeException extends SecurityException {
    public TokenTypeException() {
        super("올바른 타입의 토큰을 사용해주세요");
    }
}
