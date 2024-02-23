package com.spring.security.app.service;

import com.spring.security.app.domain.Member;
import com.spring.security.app.dto.MemberCreateRequest;
import com.spring.security.app.dto.MemberDto;
import com.spring.security.app.dto.MemberRequest;
import com.spring.security.app.jwt.JwtProvider;
import com.spring.security.app.mapper.MemberMapper;
import com.spring.security.app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService, UserDetailsService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Long memberSave(MemberCreateRequest memberCreateRequest) {
        log.info("-----mem create start-----");
        memberCreateRequest.setPassword(passwordEncoder.encode(memberCreateRequest.getPassword()));
        Member member = MemberMapper.INSTANCE.MemberCreateRequestToEntity(memberCreateRequest);
        log.info("-----mem create end-----");
        return memberRepository.save(member).getId();
    }

    @Override
    public MemberDto findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        return MemberMapper.INSTANCE.MembersToMembersDto(member);
    }


    //login 정보 인증 체크 후 토큰 발급 처리.
    @Override
    public String chkAuthentication(MemberRequest memberRequest) {

        //AuthenticationManager 에서 AuthenticationManagerBuilder 바꾸고 순환참조 해결.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberRequest.getLoginId(),memberRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtProvider.createToken(authentication);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(loginId);
    }
}
