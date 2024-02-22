package com.spring.security.app.repository;

import com.spring.security.app.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    public Optional<Member> findByUserName(String userName);
    public Member findByLoginId(String loginId);

}
