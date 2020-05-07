package com.credit.pojo;

import javax.persistence.Table;

@Table(name = "tb_worker_context")
public class TbWorkerContext {
    private Long fid;//父id

    private Long grade;//职位等级

    private String fcontext;//父菜单内容

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getFcontext() {
        return fcontext;
    }

    public void setFcontext(String fcontext) {
        this.fcontext = fcontext == null ? null : fcontext.trim();
    }
}