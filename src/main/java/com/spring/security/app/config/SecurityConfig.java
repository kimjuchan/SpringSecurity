package com.spring.security.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.app.common.api.dto.ApiErrorResponse;
import com.spring.security.app.filter.JwtAuthenticationFilter;
import com.spring.security.app.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.Date;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * AuthenticationManagerBuilder : AuthenticationManager 생성 및 구성하기위한 빌더 클래스
 * AuthenticationManager : authenticate()를 통해서 Authentication 객체를 받아 인증 처리 후 성공 시 Authentication 객체 반환
 * 실질적인 인증에대한 처리는 ProviderManager를 통해서 진행
 *
 *
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final MemberRepository memberRepository;
    private final String secretKey = "testSecretKey20230327testSecretKey20230327testSecretKey20230327";

    //private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                //url 접근 권한 설정.
                .authorizeHttpRequests(authorizeRequest  -> authorizeRequest
                        .requestMatchers("/api/user/signin").permitAll()
                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/user/auth/**").authenticated()
                        //.requestMatchers("/api/auth/info").authenticated()
                        .requestMatchers("/h2-console/**").permitAll()
                )
                .httpBasic(HttpBasicConfigurer::disable)
                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement(
                        configure ->
                                configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //cors,csrf,formLogin,logout 사용 x
                .cors(withDefaults())
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                //순환참조 에러 발생으로 잠시 주석처리.
                //.userDetailsService(userDetailsService)
                .logout(withDefaults())
                //UsernamePasswordAuthenticationFilter 전 JwtAuthenticationFilter 실행.
                .addFilterBefore(new JwtAuthenticationFilter(memberRepository, secretKey), UsernamePasswordAuthenticationFilter.class)
                /*.exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
                ) // 401 403 관련 예외처리*/
        ;
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }

    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Authentication에 대한 예외처리
    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ApiErrorResponse fail = new ApiErrorResponse("Status :: "+HttpStatus.UNAUTHORIZED.value() + " errorMessage :: "+HttpStatus.UNAUTHORIZED.getReasonPhrase(), new Date());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    //Authorization에 대한 예외처리
    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ApiErrorResponse fail = new ApiErrorResponse("Status :: "+HttpStatus.FORBIDDEN.value() + " errorMessage :: "+HttpStatus.UNAUTHORIZED.getReasonPhrase(), new Date());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

   /* *
     *
     *
     * @param passwordEncoder
     * @return UserDetails("user1"//"1234")
     *//*
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails member = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("1234"))
                .roles(Role.USER.name())
                .build();

        return new InMemoryUserDetailsManager(member);
    }
*/


}
