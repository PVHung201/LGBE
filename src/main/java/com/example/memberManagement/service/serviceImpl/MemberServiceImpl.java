package com.example.memberManagement.service.serviceImpl;

import com.example.memberManagement.Util.ExcelGeneratorUtil;
import com.example.memberManagement.dto.MemAndCountDTO;
import com.example.memberManagement.dto.MemberDTO;
import com.example.memberManagement.dto.MemberRenderDTO;
import com.example.memberManagement.dto.SearchInqDTO;
import com.example.memberManagement.entity.Member;
import com.example.memberManagement.repository.BaseRepository;
import com.example.memberManagement.repository.MemberRepository;
import com.example.memberManagement.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MemberServiceImpl extends BaseRepository implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    @Transactional
    public ResponseEntity<Object> createMember(MemberDTO memberDTO) {

        Member dupMemberId = memberRepository.findMemberById(memberDTO.getId());

        if (dupMemberId != null) {
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
        member.setMobilePhone(memberDTO.getMobilePhone());
        member.setEmail(memberDTO.getEmail());
        member.setStatus(0);
        member.setRoleId(memberDTO.getRoleId());
        member.setJoinDate(date);
        member = memberRepository.save(member);

        if (memberDTO.getEmail() != null) {
            sendNotificationEmail(memberDTO);
        }


        return new ResponseEntity<>("Registration successful", HttpStatus.OK);

    }

    @Override
    public void sendNotificationEmail(MemberDTO memberDTO) {

        String toAddress = memberDTO.getEmail();
        String fromAddress = "pvhung2001@gmail.com";
        String senderName = "Life Care Member Managemenet System";
        String subject = "Welcome to become a member of Life Care group";
        String content = "Dear [[name]],"
                + "Your account has been created on the Life Care group member management system";
        SimpleMailMessage message = new SimpleMailMessage();
        content = content.replace("[[name]]", memberDTO.getName());
        message.setFrom(fromAddress);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

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
    public MemAndCountDTO listMemberSearch(SearchInqDTO searchForm, int status, int size, int startInx) {

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
        map.put("beginDate", searchForm.getBeginDate());
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
    public List<MemberRenderDTO> listMemberSearch(SearchInqDTO searchForm, int status) {
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
    public Date addOneDay(Date oldDate) {

        Date newDate = new Date(oldDate.getTime() + (1000 * 60 * 60 * 24));
        return newDate;
    }

    public void exportExcel(HttpServletResponse response, SearchInqDTO searchForm) throws IOException {

        List<MemberRenderDTO> listMember = listMemberSearch(searchForm, searchForm.getStatus());

        String fileName = "List member " + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"" + fileName + "\"";

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(headerKey, headerValue);

        ExcelGeneratorUtil.generateExcelFile(response, listMember);
    }


}
