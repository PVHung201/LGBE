package com.example.memberManagement.model.repository;

import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member save(Member member);

}
