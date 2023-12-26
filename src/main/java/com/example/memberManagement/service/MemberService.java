package com.example.memberManagement.service;

import com.example.memberManagement.dto.MemAndCountDTO;
import com.example.memberManagement.dto.MemberDTO;
import com.example.memberManagement.dto.MemberRenderDTO;
import com.example.memberManagement.dto.SearchInqDTO;
import com.example.memberManagement.entity.Member;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface MemberService {

    ResponseEntity<Object> createMember(MemberDTO memberDTO);

    List<MemberRenderDTO> list();

    MemAndCountDTO listMemberSearch(SearchInqDTO searchForm, int status, int size, int startInx);

    List<MemberRenderDTO> listMemberSearch(SearchInqDTO searchForm, int status);

    Integer deleteMember(int id);

    Member comeBack(int memberNo);

    void sendNotificationEmail(MemberDTO memberDTO);

    void exportExcel(HttpServletResponse response, SearchInqDTO searchForm) throws IOException;
}
