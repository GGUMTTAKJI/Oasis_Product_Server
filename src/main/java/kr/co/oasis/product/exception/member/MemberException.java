package kr.co.oasis.product.exception.member;

import kr.co.oasis.product.exception.OasisException;

public abstract class MemberException extends OasisException {
    public MemberException(String message) {
        super(message);
    }
}
