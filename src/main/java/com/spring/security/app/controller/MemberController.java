package com.spring.security.app.controller;


import com.spring.security.app.common.api.advisor.ApiSuccessResponse;
import com.spring.security.app.dto.MemberCreateRequest;
import com.spring.security.app.dto.MemberDto;
import com.spring.security.app.dto.MemberRequest;
import com.spring.security.app.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class MemberController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService membersService;

    @PostMapping("/signup")
    public ResponseEntity<Void> membersCreate(@RequestBody @Valid MemberCreateRequest memberCreateRequest){
        return ResponseEntity.created(ApiSuccessResponse.createdLocation(membersService.memberSave(memberCreateRequest))).build();
    }

    @GetMapping("/signin")
    public ResponseEntity<ApiSuccessResponse<String>> memberLogin(@RequestBody MemberRequest memberRequest){
        //MemberCreateRequest 객체 유효성 체크
        String getToken = membersService.chkAuthentication(memberRequest);
        return ResponseEntity.ok(ApiSuccessResponse.create(getToken));
    }

    @GetMapping("/auth/token")
    public ResponseEntity<ApiSuccessResponse<MemberDto>> userInfo(Authentication auth) {
        MemberDto memberDto = membersService.findByLoginId(auth.getPrincipal().toString());
        return ResponseEntity.ok(ApiSuccessResponse.create(memberDto));
    }

}