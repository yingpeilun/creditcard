package com.credit.pojo;

import javax.persistence.Table;

@Table(name = "tb_worker_context")
public class TbWorkerContext {
    private Integer fid;//父id

    private Integer grade;//职位等级

    private String fcontext;//父菜单内容

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getFcontext() {
        return fcontext;
    }

    public void setFcontext(String fcontext) {
        this.fcontext = fcontext == null ? null : fcontext.trim();
    }
}