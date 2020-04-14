package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_history_not_everybill")
public class TbHistoryNotEverybill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//自增id

    private Integer ccId;//信用卡卡号

    private String payInfo;//交易描述

    private String moneyType;//交易币种

    private Integer payAmount;//交易金额·

    private Integer payDateNum;//交易日期（纯数字：20200302）

    private Integer getMoneyCard;//收款账号

    private Date payDate;//交易日期（date）

    private Integer billDateNum;//账单日（纯数字：20200316）

    private Date billDate;//账单日（date）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo == null ? null : payInfo.trim();
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType == null ? null : moneyType.trim();
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayDateNum() {
        return payDateNum;
    }

    public void setPayDateNum(Integer payDateNum) {
        this.payDateNum = payDateNum;
    }

    public Integer getGetMoneyCard() {
        return getMoneyCard;
    }

    public void setGetMoneyCard(Integer getMoneyCard) {
        this.getMoneyCard = getMoneyCard;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Integer getBillDateNum() {
        return billDateNum;
    }

    public void setBillDateNum(Integer billDateNum) {
        this.billDateNum = billDateNum;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
}