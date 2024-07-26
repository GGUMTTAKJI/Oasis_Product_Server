package kr.co.oasis.product.provider.socialAuthApi.impl;

import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.dto.AccessTokenDto;

public class GoogleSocialAuth implements SocialAuth {

    @Override
    public String getEmail(String accessToken) {
        return "";
    }

    @Override
    public AccessTokenDto getTokens(String code) {
        return null;
    }


}
