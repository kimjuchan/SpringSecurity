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
import org.springframework.security.authentication.AuthenticationManager;
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
    //private final AuthenticationManager authenticationManager;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public MemberDto memberSave(MemberCreateRequest memberCreateRequest) {

        log.info("-----mem create start-----");
        memberCreateRequest.setPassword(passwordEncoder.encode(memberCreateRequest.getPassword()));
        //param 형변환
        Member member = MemberMapper.INSTANCE.MemberCreateRequestToEntity(memberCreateRequest);
        log.info("-----MemberCreateRequest to Members Entity change-----");
        //member save
        MemberDto saveMemberDto =  MemberMapper.INSTANCE.MembersToMembersDto(memberRepository.save(member));
        Member getMem = memberRepository.findByLoginId(memberCreateRequest.getLoginId());
        if (getMem != null) {
            throw new UsernameNotFoundException("유저 정보를 찾지 못했습니다.");
        } else {
            log.info("-----Member info ::  LoginId :: " + getMem.getLoginId() + "  Pwd :: " + getMem.getPassword() + "-----");
        }
        log.info("-----mem create end-----");
        return saveMemberDto;
    }

    @Override
    public MemberDto findByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId);
        log.info("LoginId :: {}\n PWD :: {}",member.getLoginId(),member.getPassword());
        return MemberMapper.INSTANCE.MembersToMembersDto(member);
    }


    //login 정보 인증 체크 후 토큰 발급 처리.
    @Override
    public String chkAuthentication(MemberRequest memberRequest) {

        //AuthenticationManager 에서 AuthenticationManagerBuilder 바꾸고 순환참조 해결.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(memberRequest.getLoginId(),memberRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        // Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberRequest.getLoginId(),memberRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.createToken(authentication);
        return JwtProvider.createToken(authentication);

        //return "";
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(loginId);
    }
}
