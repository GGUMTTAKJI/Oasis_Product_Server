package kr.co.oasis.product.provider.socialAuthApi.dto;

import lombok.Data;

@Data
public class KakaoAccount {
    private boolean has_email;

    private boolean email_needs_agreement;

    private boolean is_email_valid;

    private boolean is_email_verified;

    private String email;
}
