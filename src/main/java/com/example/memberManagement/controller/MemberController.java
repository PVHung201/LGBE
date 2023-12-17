package com.example.memberManagement.controller;

import com.example.memberManagement.model.dto.MemAndCountDTO;
import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.dto.SearchInqDTO;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @CrossOrigin
    @PostMapping("/register")
    public  ResponseEntity<Object> createMember(@RequestBody @Validated MemberDTO memberDTO){
        ResponseEntity<Object> member = memberService.createMember(memberDTO);
        return member;
    }

    @CrossOrigin
    @GetMapping("/list")
    public List<MemberRenderDTO> listMember(){

        List<MemberRenderDTO> listMember= memberService.list();
        return listMember;
    }

    @PostMapping("/list/search")
    public MemAndCountDTO listSearch(@RequestBody SearchInqDTO searchForm){

        MemAndCountDTO listMember = memberService.listMemberSearch(searchForm, searchForm.getSize(), (searchForm.getPage())*(searchForm.getSize()));
        return listMember;
    }

}
