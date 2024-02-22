package com.spring.security.app.controller;


import com.spring.security.app.domain.Member;
import com.spring.security.app.dto.MemberCreateRequest;
import com.spring.security.app.dto.MemberDto;
import com.spring.security.app.dto.MemberRequest;
import com.spring.security.app.jwt.JwtProvider;
import com.spring.security.app.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class MemberController {
    //private final AuthenticationManager authenticationManager;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberService membersService;

    @PostMapping("/signup")
    public void membersCreate(@RequestBody @Valid MemberCreateRequest memberCreateRequest){
        //MemberCreateRequest 객체 유효성 체크
        MemberDto saveMemberDto =  membersService.memberSave(memberCreateRequest);
        /*if(saveMemberDto != null) {
            return JwtProvider.createToken(saveMemberDto.getLoginId(),"testSecretKey20230327testSecretKey20230327testSecretKey20230327",86000);
        }else {
            return "JWT Create Failed";
        }*/
    }

    @GetMapping("/signin")
    public String memberLogin(@RequestBody MemberRequest memberRequest){
        //MemberCreateRequest 객체 유효성 체크
        String getToken = membersService.chkAuthentication(memberRequest);
        return getToken;
       /* UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberRequest.getLoginId(),memberRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = JwtProvider.createToken(authentication);
        return jwt;
*/
        /*
        String secretKey = "testSecretKey20230327testSecretKey20230327testSecretKey20230327";
        long expireTimeMs = 86000;     // Token 유효 시간 = 60분

        String jwtToken = JwtProvider.createToken(memberRequest.getLoginId(), secretKey, expireTimeMs);*/


    }

    @GetMapping("/auth/token")
    public String userInfo(Authentication auth) {
        MemberDto memberDto = membersService.findByLoginId(auth.getPrincipal().toString());

        return String.format("loginId : %s\nnickname : %s",
                memberDto.getLoginId(), memberDto.getUserName());
    }


}