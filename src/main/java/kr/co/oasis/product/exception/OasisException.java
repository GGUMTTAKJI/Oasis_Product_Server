package kr.co.oasis.product.exception;

public abstract class OasisException extends RuntimeException {
    public OasisException(String message) {
        super(message);
    }
}
