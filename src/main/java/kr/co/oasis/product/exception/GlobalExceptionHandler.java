package kr.co.oasis.product.exception;

import kr.co.oasis.product.exception.member.MemberException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record BasicErrorResult(String result, String message) {}

    @ExceptionHandler({MemberException.class})
    protected BasicErrorResult handleEndPointException(Exception e) {
        return new BasicErrorResult("fail", e.getMessage());
    }
}
