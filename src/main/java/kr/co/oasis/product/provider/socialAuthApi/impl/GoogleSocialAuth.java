package kr.co.oasis.product.provider.socialAuthApi.impl;

import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccessTokenDto;

public class GoogleSocialAuth implements SocialAuth {

    @Override
    public String getEmail(String accessToken) {
        return "";
    }

    @Override
    public KakaoAccessTokenDto getTokens(String code) {
        return null;
    }


}
