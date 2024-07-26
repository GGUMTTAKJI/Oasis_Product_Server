package kr.co.oasis.product.provider.socialAuthApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoAccessTokenDto {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private int expires_in;

    private String scope;

    private int refresh_token_expires_in;

}
