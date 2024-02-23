package com.spring.security.app.filter;

import com.spring.security.app.domain.Member;
import com.spring.security.app.jwt.JwtErrorCode;
import com.spring.security.app.jwt.JwtProvider;
import com.spring.security.app.repository.MemberRepository;
import com.spring.security.app.service.MemberServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.Optional;


/**
 * 보통 시큐리티 인증, 인가에 대한 구현은    OncePerRequestFilter , GenericFilterBean을 상속받아 구현.
 * OncePerRequestFilter 상속받으면 더 좋은점
 * 만약 서블릿이 다른 서블릿으로 dispatch하게 되면, 다른 서블릿 앞단에서 filter chain을 한번 더 거치게 된다.
 * 이럴때 해당 filter를 거치게 되면서 동일한 보안 필터를 구성할 수 있음.
 *
 * BcryptPasswordEncoder 순환참조 오류 주의
 * 스프링 5.x 이상부터는 BcryptPasswordEncoder 의무적으로 써야함. -> 해당 Config 파일을 따로 만들어주어 해결.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
        if(!authorizationHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
        if(jwtProvider.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt Token에서 loginId 추출
        String userName = jwtProvider.getLoginId(token);
        // 추출한 loginId로 User 찾아오기
        Optional<Member> member = memberRepository.findByUserName(userName);
/*
        try {*/
            // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    member.get().getLoginId(), null,member.get().getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 권한 부여
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        /*}catch (ExpiredJwtException e){
            request.setAttribute("exception", JwtErrorCode.TOKEN_EXPIRED_ERROR.name());
            log.error("[ExpiredJwtException] :: {}", JwtErrorCode.TOKEN_SIGNATURE_ERROR.name());
        }catch (SignatureException e){
            request.setAttribute("exception", JwtErrorCode.TOKEN_SIGNATURE_ERROR.name());
            log.error("[SignatureException] :: {}", JwtErrorCode.TOKEN_SIGNATURE_ERROR.name());
        }catch (Exception e){
            log.error("[Exception] message: {}", e.getMessage());
        }
*/
    }
}
