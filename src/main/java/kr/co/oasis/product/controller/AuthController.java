package kr.co.oasis.product.controller;

import kr.co.oasis.product.dto.resp.MemberResp;
import kr.co.oasis.product.security.jwt.TokenDto;
import kr.co.oasis.product.security.jwt.TokenProvider;
import kr.co.oasis.product.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @GetMapping("/login")
    public ResponseEntity<MemberResp.login> login(@RequestParam String code) {
        log.info("code:{}", code);
        TokenDto token = authService.login(code);
        return ResponseEntity.ok(new MemberResp.login("success", "loginOk", token));
    }

    @GetMapping("/join")
    public ResponseEntity<MemberResp.login> join(@RequestParam String name, @RequestParam String phone, @RequestParam String email) {
        log.info("name:{}, phone:{}, email:{}", name, phone, email);
        TokenDto token = authService.join(name, phone, email);
        return ResponseEntity.ok(new MemberResp.login("success", "joinOk", token));
    }



}