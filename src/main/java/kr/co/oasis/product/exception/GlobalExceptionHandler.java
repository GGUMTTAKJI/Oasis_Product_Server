package kr.co.oasis.product.exception;

import kr.co.oasis.product.exception.member.AlreadyMemberException;
import kr.co.oasis.product.exception.member.MemberException;
import kr.co.oasis.product.exception.member.NotMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record BasicErrorResult(String result, String message, String data) {}

    @ExceptionHandler({MemberException.class})
    protected ResponseEntity<BasicErrorResult> handleEndPointException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BasicErrorResult("fail", e.getMessage(), null));
    }

    @ExceptionHandler({NotMemberException.class})
    protected ResponseEntity<BasicErrorResult> handleNotMemberException(NotMemberException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BasicErrorResult("fail", e.getMessage(), e.getEmail()));
    }

    @ExceptionHandler({AlreadyMemberException.class})
    protected ResponseEntity<BasicErrorResult> handleAlreadyMemberException(NotMemberException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new BasicErrorResult("fail", e.getMessage(), e.getEmail()));
        //https://deveric.tistory.com/62 에러코드 선정 이유
    }



}
