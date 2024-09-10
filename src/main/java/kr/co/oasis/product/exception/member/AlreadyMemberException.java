package kr.co.oasis.product.exception.member;

import lombok.Getter;

@Getter
public class AlreadyMemberException extends MemberException {
    String email;
    public AlreadyMemberException() { super("이미 가입되어있는 회원입니다."); }

    public AlreadyMemberException(String email) {
        super("이미 가입되어있는 회원입니다.");  // 예외 메시지 설정
        this.email = email;  // 추가적으로 이메일 정보 저장
    }

}
