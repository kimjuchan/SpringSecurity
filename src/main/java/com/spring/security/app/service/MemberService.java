package com.spring.security.app.service;

import com.spring.security.app.dto.MemberCreateRequest;
import com.spring.security.app.dto.MemberDto;
import com.spring.security.app.dto.MemberRequest;

public interface MemberService {

    public Long memberSave(MemberCreateRequest memberCreateRequest);
    public MemberDto findByLoginId(String loginId);
    public String chkAuthentication(MemberRequest memberRequest);
}
