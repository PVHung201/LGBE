package com.example.memberManagement.service;

import com.example.memberManagement.model.dto.MemAndCountDTO;
import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.dto.SearchInqDTO;
import com.example.memberManagement.model.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    Member createMember(MemberDTO memberDTO);

    List<MemberRenderDTO> list();

    MemAndCountDTO listMemberSearch(SearchInqDTO searchForm, int size, int startInx);


}
