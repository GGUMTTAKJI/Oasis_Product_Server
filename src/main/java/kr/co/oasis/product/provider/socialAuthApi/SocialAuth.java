package kr.co.oasis.product.provider.socialAuthApi;

import kr.co.oasis.product.provider.socialAuthApi.dto.AccessTokenDto;

import java.util.HashMap;

public interface SocialAuth {

    String getEmail(String accessToken);

    AccessTokenDto getTokens(String code);
}
