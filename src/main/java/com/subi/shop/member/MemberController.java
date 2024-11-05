package com.subi.shop.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final  PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/register")
    public String register(Authentication auth) {
        if (auth.isAuthenticated()){
            return "redirect:/list";
        }
        return "register.html";
    }

    @PostMapping("/member")
    String addMember( String username, String password, String displayName) {
        Member member = new Member();
        member.setUsername(username);
        var hash = passwordEncoder.encode(password);
        member.setPassword(hash);
        member.setDisplayName(displayName);

        this.memberRepository.save(member);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }


    @GetMapping("/my-page")
    public String myPage(Authentication auth, Principal p) {
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);

        return "mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();

        var data = new MemberDto(result.getUsername(), result.getDisplayName(), result.getId());
        return data;
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response
                           ){

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        // 로그인을 수동으로 처리하면 Authentication auth에 유저정보 자동으로 들어가지 않아 수동으로 넣어줘야 함.
        // SecurityContextHolder.getContext().getAuthentication(); // auth 가져옴

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        // 쿠키 저장
       var cookie =  new Cookie("jwt",jwt);
        cookie.setMaxAge(10); // 10초
        cookie.setHttpOnly(true); // 외부인들이 자바스크립트로 쿠키 조작하는거 막음
        cookie.setPath("/"); // 쿠키가 전송될 경로(또는 하위경로) 지정 // 모든 경로로 지정함
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    public String myPageJWT(Authentication auth) {
        //  JWT 까보고 로그인잘되어있으면 마이페이지 보내주기~
        var user = (CustomUser) auth.getPrincipal();

        return "";
    }
}

class MemberDto {
    public String username;
    public String displayName;
    public Long id;
    MemberDto(String a, String b) {
        this.username = a;
        this.displayName = b;
    }
    MemberDto(String a, String b, Long c) {
        this.username = a;
        this.displayName = b;
        this.id = c;
    }

}