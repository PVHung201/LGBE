package com.example.memberManagement.controller;

import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @CrossOrigin
    @PostMapping("/register")
    public Member createMember(@RequestBody @Validated MemberDTO memberDTO){
        Member member = memberService.createMember(memberDTO);
        return member;
    }

    @CrossOrigin
    @GetMapping("/list")
    public List<MemberRenderDTO> listMember(){
        List<MemberRenderDTO> listMember= memberService.list();
        return listMember;
    }
}
