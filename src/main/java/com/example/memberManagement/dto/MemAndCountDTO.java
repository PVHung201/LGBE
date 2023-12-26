package com.example.memberManagement.dto;

import java.util.List;

public class MemAndCountDTO {
    private List<MemberRenderDTO> listMemberRen;

    private int count;

    public List<MemberRenderDTO> getListMemberRen() {
        return listMemberRen;
    }

    public void setListMemberRen(List<MemberRenderDTO> listMemberRen) {
        this.listMemberRen = listMemberRen;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
