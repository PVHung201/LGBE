package com.example.memberManagement.model.repository;

import com.example.memberManagement.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member save(Member member);

    //List<Member> findAllBetween(int startInx, int endInx);

    //@Query(value = "SELECT * FROM Members LIMIT ?1 OFFSET ?2 ", nativeQuery = true)
    List<Member> findAll();


    Member findMemberById(String id);

//    @Query("UPDATE members SET status = 1 WHERE number_no = ?1")
//    public Integer deleteMember(Integer int);

    public Member findMemberByMemberNo(int numberNo);

}
