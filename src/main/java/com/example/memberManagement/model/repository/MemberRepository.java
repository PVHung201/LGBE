package com.example.memberManagement.model.repository;

import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member save(Member member);

    //List<Member> findAllBetween(int startInx, int endInx);

    @Query(value = "SELECT * FROM Members WHERE LIKE like '%?1%' AND id LIKE '%?2' AND AND AND' LIMIT ?1 OFFSET ?2 ", nativeQuery = true)
    List<Member> findMemberByPage(Integer size, Integer startInx, String Id, String name, String phone, Date joinDate);

    Member findMemberById(String id);

}
