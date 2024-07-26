package kr.co.oasis.product.repository;

import kr.co.oasis.product.entity.domain.Member;
import kr.co.oasis.product.entity.enums.LoginType;
import kr.co.oasis.product.entity.enums.MemberRole;
import kr.co.oasis.product.entity.enums.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository userRepository;

    @Test
    @DisplayName("이메일로 사용자 찾기 테스트")
    public void findByEmail_Success() throws Exception {
        //given
        String testEmail = "test@email.com";
        Member member = Member.builder()
                .id(1L)
                .loginType(LoginType.SIMPLE)
                .password("testpw")
                .name("testName")
                .phone("01000000000")
                .email(testEmail)
                .role(MemberRole.MEMBER)
                .memberStatus(MemberStatus.ACTIVE)
                .build();
        userRepository.save(member);
        //when
        Optional<Member> result = userRepository.findByEmail(testEmail);
        //then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(testEmail);
    }
}