package kr.co.oasis.product.service;

import kr.co.oasis.product.entity.domain.Member;
import kr.co.oasis.product.entity.enums.LoginType;
import kr.co.oasis.product.entity.enums.MemberRole;
import kr.co.oasis.product.entity.enums.MemberStatus;
import kr.co.oasis.product.exception.member.AlreadyMemberException;
import kr.co.oasis.product.exception.member.NotMemberException;
import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccessTokenDto;
import kr.co.oasis.product.repository.MemberRepository;
import kr.co.oasis.product.repository.redis.RedisRepository;
import kr.co.oasis.product.security.jwt.TokenDto;
import kr.co.oasis.product.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final SocialAuth<KakaoAccessTokenDto> kakaoSocialAuth;
    private final SocialAuth googleSocialAuth;
    private final SocialAuth naverSocialAuth;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;
    private final String ACCESS_TOKEN_KEY = "accessToken";
    private final String REFRESH_TOKEN_KEY = "refreshToken";
    private final String GENERATE_TIME = "generateTime";

    @Value("${jwt.access-expire-time}")
    private long accessExpirationTime;
    @Value("${jwt.refresh-expire-time}")
    private long refreshExpirationTime;

    public TokenDto login(String code) {
        KakaoAccessTokenDto tokens = kakaoSocialAuth.getTokens(code);
        String email = kakaoSocialAuth.getEmail(tokens.getAccess_token());
        Optional<Member> result = memberRepository.findByEmail(email);

        if (result.isEmpty()) { // 디비에 데이터가 없으면 회원이 아님으로 예외처리
            throw new NotMemberException();
        }

        String redisKey = email + ":token";

        //Redis에 토큰 정보가 있는지 확인
        if(redisRepository.hasKey(redisKey)) {
            // 있다면 Redis에 저장되어 있는 토큰 리턴
            String accessToken = redisRepository.getHashValue(redisKey, ACCESS_TOKEN_KEY).toString();
            String refreshToken = redisRepository.getHashValue(redisKey, REFRESH_TOKEN_KEY).toString();
            log.info("로그인 성공, 토큰 재사용");
            log.info("토큰 : {}", accessToken);
            return new TokenDto(accessToken, refreshToken);
        }

        //Redis에 토큰이 없다면 새로 발급
        TokenDto token = tokenProvider.createToken(email, result.get().getRole().toString());

        Map<String, Object> tokenMap = new HashMap<>();
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss"));
        tokenMap.put(ACCESS_TOKEN_KEY, token.getAccessToken());
        tokenMap.put(REFRESH_TOKEN_KEY, token.getRefreshToken());
        tokenMap.put(GENERATE_TIME, formattedDateTime);

        Duration refreshTtl = Duration.ofDays(7); // 리프레시 토큰의 Redis 저장 기간을 일주일로 설정
        redisRepository.setHashValues(redisKey, tokenMap); //Redis에 리프레쉬 토큰 저장

        log.info("로그인 성공, 토큰 신규 발급");
        log.info("토큰 : {}", token.getAccessToken());

        return token;
    }

    public TokenDto join(String name, String phone, String email) {
        //이메일로 이미 존재하는 회원인지 체크
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            throw new AlreadyMemberException();
        }

        //DB에 회원정보 저장
        Member member = Member.builder()
                .loginType(LoginType.KAKAO)
                .name(name)
                .email(email)
                .phone(phone)
                .role(MemberRole.MEMBER)
                .memberStatus(MemberStatus.ACTIVE)
                .build();
        memberRepository.save(member);
        log.info("회원가입 정보 저장 성공");

        //로그인용 JWT토큰 발급
        String redisKey = email + ":token";

        TokenDto token = tokenProvider.createToken(email, member.getRole().toString());

        Map<String, Object> tokenMap = new HashMap<>();
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss"));
        tokenMap.put(ACCESS_TOKEN_KEY, token.getAccessToken());
        tokenMap.put(REFRESH_TOKEN_KEY, token.getRefreshToken());
        tokenMap.put(GENERATE_TIME, formattedDateTime);

        Duration refreshTtl = Duration.ofDays(7); // 리프레시 토큰의 Redis 저장 기간을 일주일로 설정
        redisRepository.setHashValues(redisKey, tokenMap); //Redis에 리프레쉬 토큰 저장

        log.info("로그인 성공, 토큰 신규 발급");
        log.info("토큰 : {}", token.getAccessToken());

        return token;

    }

}