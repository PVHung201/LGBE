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
    public List<MemberRenderDTO> listMember(@RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "size", required = true) Integer size){

        List<MemberRenderDTO> listMember= memberService.list(size, page*size);
        return listMember;
    }

    @GetMapping("/list/search")
    public List<MemberRenderDTO> listSearch(@RequestParam(name = "searchForm") MemberRenderDTO searchForm,
                                            @RequestParam(value = "page", required = false) Integer page,
                                            @RequestParam(value = "size", required = true) Integer size){

        List<MemberRenderDTO> listMember = memberService.listMemberSearch(searchForm, size, page*size);
        return listMember;

    }




}
