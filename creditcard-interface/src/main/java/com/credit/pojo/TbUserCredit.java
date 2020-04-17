package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_user_credit")
public class TbUserCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id

    private Long ccId;//信用卡卡号

    private Long overdueAmount;//逾期金额

    private Long overdueDay;//逾期天数

    private Long overdueNum;//逾期次数

    private Long creditScore;//信用评分（用于划分用户等级）

    private String realname;//真实姓名

    private String idCard;//身份证号

    private Long clientCreditStatus;//客户信用状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCcId() {
        return ccId;
    }

    public void setCcId(Long ccId) {
        this.ccId = ccId;
    }

    public Long getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Long overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Long getOverdueDay() {
        return overdueDay;
    }

    public void setOverdueDay(Long overdueDay) {
        this.overdueDay = overdueDay;
    }

    public Long getOverdueNum() {
        return overdueNum;
    }

    public void setOverdueNum(Long overdueNum) {
        this.overdueNum = overdueNum;
    }

    public Long getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Long creditScore) {
        this.creditScore = creditScore;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Long getClientCreditStatus() {
        return clientCreditStatus;
    }

    public void setClientCreditStatus(Long clientCreditStatus) {
        this.clientCreditStatus = clientCreditStatus;
    }
}