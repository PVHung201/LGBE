package com.example.memberManagement.controller;

import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class registerController {

    @Autowired
    MemberService memberService;

    @PostMapping
    public Member createMember(@RequestBody @Validated MemberDTO memberDTO){
        Member member = memberService.createMember(memberDTO);
        return member;
    }
}
