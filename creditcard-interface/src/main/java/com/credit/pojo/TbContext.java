package com.credit.pojo;

import javax.persistence.Table;

@Table(name = "tb_context")
public class TbContext {

    private Integer sid;//子id

    private Integer fid;//父id

    private String scontext;//子菜单内容

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getScontext() {
        return scontext;
    }

    public void setScontext(String scontext) {
        this.scontext = scontext == null ? null : scontext.trim();
    }
}