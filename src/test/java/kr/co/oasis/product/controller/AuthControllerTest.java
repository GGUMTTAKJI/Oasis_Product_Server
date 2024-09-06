package kr.co.oasis.product.controller;

import kr.co.oasis.product.exception.GlobalExceptionHandler;
import kr.co.oasis.product.exception.member.NotMemberException;
import kr.co.oasis.product.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Import(GlobalExceptionHandler.class) // Ensure the GlobalExceptionHandler is used
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setControllerAdvice(globalExceptionHandler) // Add this to activate the exception handler
                .build();
    }

    @Test
    public void login_success() throws Exception {
        // Mock behavior for a successful login
        when(authService.login(anyString())).thenReturn("JWT_TOKEN");

        // Perform login and check expectations
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login")
                        .param("code", "testCode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("loginOk"))
                .andExpect(jsonPath("$.token").value("JWT_TOKEN"));
    }

    @Test
    public void login_fail_notMember() throws Exception {

        when(authService.login("abcd")).thenThrow(NotMemberException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login")
                        .param("code", "testCode")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("fail"))
                .andExpect(jsonPath("$.message").value("notMember"));
    }
}
