package com.example.memberManagement.service.serviceImpl;

import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.model.repository.MemberRepository;
import com.example.memberManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member createMember(MemberDTO memberDTO){
        Member member = new Member();

        member.setMemberNo(0);
        member.setId(memberDTO.getId());
        member.setPassword(memberDTO.getPassword());
        member.setName(memberDTO.getName());
        member.setMobilePhone(String.valueOf(memberDTO.getMobilePhone()));
        member.setEmail(memberDTO.getEmail());
        member.setStatus(0);
        member.setRoleId(memberDTO.getRoleId());

        member = memberRepository.save(member);

        return member;



    }
}
