package kr.co.oasis.product.provider.socialAuthApi;

import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccessTokenDto;

public interface SocialAuth <T>{

    String getEmail(String accessToken);

    T getTokens(String code);
}