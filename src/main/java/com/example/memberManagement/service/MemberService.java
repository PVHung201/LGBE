package com.example.memberManagement.service;

import com.example.memberManagement.model.dto.MemAndCountDTO;
import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.dto.SearchInqDTO;
import com.example.memberManagement.model.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    ResponseEntity<Object> createMember(MemberDTO memberDTO);

    List<MemberRenderDTO> list();

    MemAndCountDTO listMemberSearch(SearchInqDTO searchForm, int status, int size, int startInx);

    List<MemberRenderDTO> listMemberSearch(SearchInqDTO searchForm);

    Integer deleteMember(int id);

    Member comeBack(int memberNo);


}
