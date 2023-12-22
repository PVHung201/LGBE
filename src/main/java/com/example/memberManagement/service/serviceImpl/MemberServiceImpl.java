package com.example.memberManagement.service.serviceImpl;

import com.example.memberManagement.model.dto.MemAndCountDTO;
import com.example.memberManagement.model.dto.MemberDTO;
import com.example.memberManagement.model.dto.MemberRenderDTO;
import com.example.memberManagement.model.dto.SearchInqDTO;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.model.repository.BaseRepository;
import com.example.memberManagement.model.repository.MemberRepository;
import com.example.memberManagement.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MemberServiceImpl extends BaseRepository implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public ResponseEntity<Object> createMember(MemberDTO memberDTO) {

        Member dupMemberId = memberRepository.findMemberById(memberDTO.getId());

        if(dupMemberId != null){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Duplicate ID");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Member member = new Member();
        Date date = new Date();
        member.setMemberNo(0);
        member.setId(memberDTO.getId());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setName(memberDTO.getName());
        member.setMobilePhone(String.valueOf(memberDTO.getMobilePhone()));
        member.setEmail(memberDTO.getEmail());
        member.setStatus(0);
        member.setRoleId(memberDTO.getRoleId());
        member.setJoinDate(date);
        member = memberRepository.save(member);

        return new ResponseEntity<>("Registration successful", HttpStatus.OK);

    }

    @Override
    public List<MemberRenderDTO> list() {
        List<MemberRenderDTO> listMemberRender = new ArrayList<MemberRenderDTO>();

        List<Member> listMember = memberRepository.findAll();

        for (Member member : listMember) {
            MemberRenderDTO memberRender = new MemberRenderDTO();
            memberRender.setMemberNo(member.getMemberNo());
            memberRender.setId(member.getId());
            memberRender.setName(member.getName());
            memberRender.setEmail(member.getEmail());
            memberRender.setMobilePhone(member.getMobilePhone());
            memberRender.setJoinDate(member.getJoinDate());

            listMemberRender.add(memberRender);

        }
        return listMemberRender;
    }

    @Override
    public MemAndCountDTO listMemberSearch(SearchInqDTO searchForm,int status, int size, int startInx) {

        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT " +
                "members.member_no, " +
                "members.id id, " +
                "members.name name, " +
                "members.mobile_phone, " +
                "members.email, " +
                "members.join_date " +
                "FROM members WHERE 1 = 1 ");

        sql.append("AND members.status = :status ");
        map.put("status", status);

        if (searchForm.getId() != null && searchForm.getId().length() > 2) {
            sql.append("AND members.id LIKE :id ");
            map.put("id", "%" + searchForm.getId() + "%");
        }

        if (searchForm.getName() != null && searchForm.getName().length() > 1) {
            sql.append("AND members.name LIKE :name ");
            map.put("name", "%" + searchForm.getName() + "%");
        }

        if (searchForm.getMobilePhone() != null && !searchForm.getMobilePhone().isEmpty()) {
            sql.append("AND members.mobile_phone LIKE :mobilePhone ");
            map.put("mobilePhone", "%" + searchForm.getMobilePhone() + "%");
        }

        sql.append("AND members.join_date BETWEEN :beginDate AND :endDate ");
        map.put("beginDate", searchForm.getBeginDate() );
        map.put("endDate", addOneDay(searchForm.getEndDate()));


        Integer count = getNamedParameterJdbcTemplateNormal().
                query(sql.toString(), map, BeanPropertyRowMapper.newInstance(MemberRenderDTO.class))
                .size();

        sql.append("LIMIT :size OFFSET :startInx");
        map.put("startInx", startInx);
        map.put("size", size);

        List<MemberRenderDTO> dataTable = getNamedParameterJdbcTemplateNormal()
                .query(sql.toString(), map, BeanPropertyRowMapper.newInstance(MemberRenderDTO.class));

        MemAndCountDTO result = new MemAndCountDTO();

        result.setListMemberRen(dataTable);
        System.out.println(result.getListMemberRen());
        result.setCount(count);


        return result;

    }

    @Override
    public List<MemberRenderDTO> listMemberSearch(SearchInqDTO searchForm) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT " +
                "members.member_no, " +
                "members.id id, " +
                "members.name name, " +
                "members.mobile_phone, " +
                "members.email, " +
                "members.join_date " +
                "FROM members WHERE 1 = 1 AND status = 0 ");

        if (searchForm.getId() != null && searchForm.getId().length() > 2) {
            sql.append("AND members.id LIKE :id ");
            map.put("id", "%" + searchForm.getId() + "%");
        }

        if (searchForm.getName() != null && searchForm.getName().length() > 1) {
            sql.append("AND members.name LIKE :name ");
            map.put("name", "%" + searchForm.getName() + "%");
        }

        if (searchForm.getMobilePhone() != null && !searchForm.getMobilePhone().isEmpty()) {
            sql.append("AND members.mobile_phone LIKE :mobilePhone ");
            map.put("mobilePhone", "%" + searchForm.getMobilePhone() + "%");
        }

        sql.append("AND members.join_date BETWEEN :beginDate AND :endDate ");
        map.put("beginDate", searchForm.getBeginDate());
        map.put("endDate", addOneDay(searchForm.getEndDate()));

        List<MemberRenderDTO> dataTable = getNamedParameterJdbcTemplateNormal()
                .query(sql.toString(), map, BeanPropertyRowMapper.newInstance(MemberRenderDTO.class));

        List<MemberRenderDTO> result = new ArrayList<>();

        result = dataTable;
        System.out.println(result);

        return result;
    }

    @Transactional
    @Override
    public Integer deleteMember(int numberNo) {
        Member deleteMember = memberRepository.findMemberByMemberNo(numberNo);
        deleteMember.setStatus(1);
        Member delMemer = memberRepository.save(deleteMember);
        return 0;
    }

    @Transactional
    @Override
    public Member comeBack(int memberNo) {
        Member memberComeBack = memberRepository.findMemberByMemberNo(memberNo);
        memberComeBack.setStatus(0);
        Member comeBack = memberRepository.save(memberComeBack);
        return comeBack;
    }

    // Add 1 day to endDate
    public Date addOneDay(Date oldDate){

        Date newDate = new Date(oldDate.getTime() + (1000*60*60*24));
        return newDate;
    }


}
