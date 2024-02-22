package com.spring.security.app.mapper;

import com.spring.security.app.domain.Member;
import com.spring.security.app.dto.MemberCreateRequest;
import com.spring.security.app.dto.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

//Mapping 하지 않은 값에 대한 오류 넘김 처리를 위해서 IGNORE 처리.
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    //Mapper 설정.
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    //MemberCreateRequest TO MembersDto Type 변환.
    Member MemberCreateRequestToEntity(MemberCreateRequest memberCreateRequest);

    //MembersDto TO Members Entity Type 변환
    Member MembersDtoToMembers(MemberDto membersDto);

    //Member TO MembersDto Type 변환
    MemberDto MembersToMembersDto(Member members);



}
