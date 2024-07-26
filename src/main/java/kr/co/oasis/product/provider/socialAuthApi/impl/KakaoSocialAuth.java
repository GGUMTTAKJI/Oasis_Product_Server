package kr.co.oasis.product.provider.socialAuthApi.impl;

import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.dto.AccessTokenDto;
import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccount;
import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RequiredArgsConstructor
public class KakaoSocialAuth implements SocialAuth {

    private final WebClient webClient;

    @Value("${social.kakaoKey}")
    private String restKey;

    @Override
    public String getEmail(String accessToken) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                        .host("kapi.kakao.com")
                        .path("/v2/user/me")
                        .build()
                )
                .headers(httpHeaders -> httpHeaders.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(KakaoResponse.class)
                .map(KakaoResponse::getKakao_account)
                .map(KakaoAccount::getEmail)
                .block();
    }

    @Override
    public AccessTokenDto getTokens(String code) {
        Mono<AccessTokenDto> accessTokenDtoMono = webClient
                .post()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                        .host("kauth.kakao.com")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", restKey)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(AccessTokenDto.class);

        return accessTokenDtoMono.block();
    }

}
