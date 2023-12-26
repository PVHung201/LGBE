package com.example.memberManagement.controller;

import com.example.memberManagement.dto.MemAndCountDTO;
import com.example.memberManagement.dto.MemberDTO;
import com.example.memberManagement.dto.MemberRenderDTO;
import com.example.memberManagement.dto.SearchInqDTO;
import com.example.memberManagement.entity.Member;
import com.example.memberManagement.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Object> createMember(@RequestBody @Validated MemberDTO memberDTO) {
        ResponseEntity<Object> member = memberService.createMember(memberDTO);
        return member;
    }

    @GetMapping("/list")
    public List<MemberRenderDTO> listMember() {

        List<MemberRenderDTO> listMember = memberService.list();
        return listMember;
    }

    @PostMapping("/search")
    public MemAndCountDTO listSearch(@RequestBody SearchInqDTO searchForm) {

        MemAndCountDTO listMember = memberService.listMemberSearch(searchForm, searchForm.getStatus(), searchForm.getSize(), (searchForm.getPage()) * (searchForm.getSize()));
        return listMember;
    }


    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody SearchInqDTO searchForm, HttpServletResponse response) throws IOException {

        memberService.exportExcel(response, searchForm);


    }


    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMember(@PathVariable(name = "id") int id) {
        int deleteMember = memberService.deleteMember(id);
        return new ResponseEntity<>(deleteMember, HttpStatus.OK);
    }

    @PutMapping("/comeBack/{id}")
    public ResponseEntity<Member> memComeBack(@PathVariable(name = "id") int memberNo) {
        Member memberComeBack = memberService.comeBack(memberNo);
        return new ResponseEntity<>(memberComeBack, HttpStatus.OK);
    }


}
