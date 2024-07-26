package kr.co.oasis.product.controller;

import kr.co.oasis.product.dto.resp.MemberResp;
import kr.co.oasis.product.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public MemberResp.login login(@RequestParam String code) {
        log.info("code:{}", code);
        String login = memberService.login(code);
        return new MemberResp.login("success", "loginOk", "JWT_TOKEN");
    }

}