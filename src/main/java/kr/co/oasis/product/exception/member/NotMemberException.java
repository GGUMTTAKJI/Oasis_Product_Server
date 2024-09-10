package kr.co.oasis.product.exception.member;

import lombok.Getter;

@Getter
public class NotMemberException extends MemberException {
    String email;
    public NotMemberException() { super("존재하지 않는 회원입니다."); }

    public NotMemberException(String email) {
        super("존재하지 않는 회원입니다.");  // 예외 메시지 설정
        this.email = email;  // 추가적으로 이메일 정보 저장
    }

}
