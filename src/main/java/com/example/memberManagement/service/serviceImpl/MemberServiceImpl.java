package com.example.memberManagement.service.serviceImpl;

import com.example.memberManagement.model.dto.AuthenResponse;
import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.model.repository.MemberRepository;
import com.example.memberManagement.service.JwtService;
import com.example.memberManagement.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibou.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
     JwtService jwtService;

    @Override
    public AuthenResponse createMember(MemberDTO memberDTO){
        Member member = new Member();

        Date date = new Date();

        var member = Member.Builder().memberNo(memberDTO.getMemberNo())
                .member



//        member.setMemberNo(0);
//        member.setId(memberDTO.getId());
//        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
//        member.setName(memberDTO.getName());
//        member.setMobilePhone(String.valueOf(memberDTO.getMobilePhone()));
//        member.setEmail(memberDTO.getEmail());
//        member.setStatus(0);
//        member.setRoleId(memberDTO.getRoleId());
//        member.setJoinDate(date);
//        member = memberRepository.save(member);
//
//        var jwtToken = jwtService.generateAccessToken(member);

        return member;

    }

    @Override
    public List<MemberRenderDTO> list() {
        List<MemberRenderDTO> listMemberRender = new ArrayList<MemberRenderDTO>();

        List<Member> listMember = memberRepository.findAll();

        for(Member member : listMember){
            MemberRenderDTO memberRender = new MemberRenderDTO();
            memberRender.setMemberNo(member.getMemberNo());
            memberRender.setId(member.getId());
            memberRender.setName(member.getName());
            memberRender.setEmail(member.getEmail());
            memberRender.setMobilePhone(member.getMobilePhone());
            memberRender.setJoinDate(member.getJoinDate());

            listMemberRender.add(memberRender);

        }
        return listMemberRender;
    }
}
