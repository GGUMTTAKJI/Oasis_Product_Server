package kr.co.oasis.product.config;

import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.impl.GoogleSocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.impl.KakaoSocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.impl.NaverSocialAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public SocialAuth kakaoSocialAuth() {
        return new KakaoSocialAuth(webClient());
    }

    @Bean
    public SocialAuth googleSocialAuth() {
        return new GoogleSocialAuth();
    }

    @Bean
    public SocialAuth naverSocialAuth() {
        return new NaverSocialAuth();
    }

}
