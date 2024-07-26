package kr.co.oasis.product.service;

import kr.co.oasis.product.entity.domain.Member;
import kr.co.oasis.product.exception.member.NotMemberException;
import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccessTokenDto;
import kr.co.oasis.product.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final SocialAuth kakaoSocialAuth;
    private final SocialAuth googleSocialAuth;
    private final SocialAuth naverSocialAuth;
    private final MemberRepository memberRepository;

    public String login(String code) {
        KakaoAccessTokenDto tokens = kakaoSocialAuth.getTokens(code);
        log.info("access token:{}", tokens);
        String email = kakaoSocialAuth.getEmail(tokens.getAccess_token());
        log.info("email:{}", email);
        Optional<Member> result = memberRepository.findByEmail(email);

        log.info("result:{}", result.isEmpty());

        if (result.isEmpty()) { // 디비에 데이터가 없으면 회원이 아님으로 예외처리
            throw new NotMemberException();
        }

        //TODO : JWT 토큰 발급해줘야함


        return result.get().getEmail();
    }

}