package kr.co.oasis.product.provider.socialAuthApi;

import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccessTokenDto;

public interface SocialAuth {

    String getEmail(String accessToken);

    KakaoAccessTokenDto getTokens(String code);
}
