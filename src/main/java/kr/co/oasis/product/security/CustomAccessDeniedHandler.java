package kr.co.oasis.product.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.oasis.product.dto.resp.NetworkError;
import kr.co.oasis.product.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 권한이 없는 Jwt로 요청이 오면 경고 로그를 출력하고 에러메세지를 돌려준다
        String reqToken = jwtAuthenticationFilter.parseBearerToken(request);
        User user = jwtAuthenticationFilter.parseUserSpecification(reqToken);
        sendError(response, HttpStatus.FORBIDDEN, new NetworkError("fail", "접근 권한이 없습니다"));
    }

    private void sendError(HttpServletResponse response, HttpStatus status, NetworkError errorMessage) throws IOException { // HTTP 에러 메세지 보내주는 메서드
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8"); // 문자 인코딩을 UTF-8로 설정
        response.setContentType("application/json"); // JSON 형식으로 데이터를 처리
        response.getWriter().write(errorMessage.toString());
    }
}
