package com.spring.security.app.dto;


import com.spring.security.app.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String loginId;
    private String userName;
    private String email;
    private RoleType roleType;
}
