package kr.co.oasis.product.service;

import kr.co.oasis.product.entity.domain.Member;
import kr.co.oasis.product.exception.member.NotMemberException;
import kr.co.oasis.product.provider.socialAuthApi.SocialAuth;
import kr.co.oasis.product.provider.socialAuthApi.dto.KakaoAccessTokenDto;
import kr.co.oasis.product.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private SocialAuth socialAuth;

    @Mock
    private MemberRepository userRepository;

    @InjectMocks
    private MemberService userService;

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success() throws Exception {
        //given
        String code = "RESTAPI_CODE";
        KakaoAccessTokenDto token = new KakaoAccessTokenDto("test", "test","test",1,"test",1);
        //when
        Member user = new Member("test@email.com");
        when(socialAuth.getTokens(code)).thenReturn(token);
        when(socialAuth.getEmail(token.getAccess_token())).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        //then
        String result = userService.login(code);

        assertThat(result).isEqualTo("test@email.com");

    }

    @Test
    @DisplayName("회원 없음 예외 테스트")
    public void login_notMember_exception() throws Exception {
        //given
        String code = "RESTAPI_CODE";
        KakaoAccessTokenDto token = new KakaoAccessTokenDto("test", "test","test",1,"test",1);
        //when
        when(socialAuth.getTokens(code)).thenReturn(token);
        when(socialAuth.getEmail(token.getAccess_token())).thenReturn("test@email.com");
        when(userRepository.findByEmail("test@email.com")).thenThrow(NotMemberException.class);
        //then
        assertThrows(NotMemberException.class, () -> userService.login(code));
    }

}