package com.example.memberManagement.service;

import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.entity.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    Member createMember(MemberDTO memberDTO);
}
