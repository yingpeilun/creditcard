package com.credit.pojo;

import javax.persistence.Table;

@Table(name = "tb_context")
public class TbContext {

    private Long sid;//子id

    private Long fid;//父id

    private String scontext;//子菜单内容

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getScontext() {
        return scontext;
    }

    public void setScontext(String scontext) {
        this.scontext = scontext == null ? null : scontext.trim();
    }
}