package kr.co.oasis.product.dto.resp;

public record NetworkError(String result, String message) {
    @Override
    public String toString() {
        return "{\"result\":\"" + result + "\",\"message\":\"" + message + "\"}";
    }
}
