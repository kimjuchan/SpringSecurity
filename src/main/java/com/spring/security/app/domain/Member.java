package com.spring.security.app.domain;


import com.spring.security.app.common.BaseEntity;
import com.spring.security.app.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    //계정 만료 체크
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김체크
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비번만료 체크
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //사용가능한 계정인지.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
