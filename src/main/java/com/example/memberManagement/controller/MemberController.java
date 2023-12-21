package com.example.memberManagement.controller;

import com.example.memberManagement.common.ExcelGenerator;
import com.example.memberManagement.model.dto.MemAndCountDTO;
import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.dto.SearchInqDTO;
import com.example.memberManagement.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody SearchInqDTO searchForm, HttpServletResponse response) throws IOException {
        List<MemberRenderDTO> listMember = memberService.listMemberSearch(searchForm);
        String fileName = "List member " + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"" + fileName + "\"";


        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(headerKey, headerValue);
        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ExcelGenerator generator = new ExcelGenerator(listMember);
        generator.generateExcelFile(response);


    }

    @PutMapping("/delete/{id}")
    public int deleteMember(@PathVariable(name = "id") int id){
        int deleteMember = memberService.deleteMember(id);
        return deleteMember;
    }



}
