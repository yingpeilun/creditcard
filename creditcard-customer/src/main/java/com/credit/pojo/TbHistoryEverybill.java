package com.credit.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_history_everybill")
public class TbHistoryEverybill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id

    private Long ccId;//信用卡卡号

    private String payInfo;//交易描述

    private String moneyType;//交易币种

    private Long payAmount;//交易金额·

    private Long payDateNum;//交易日期（纯数字：20200302）

    private Long getMoneyCard;//收款账号
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date payDate;//交易日期（date）

    private Long billDateNum;//账单日（纯数字：20200316）
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date billDate;//账单日（date）

    private Long repayDateNum;//还款日（纯数字：20200316）
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date repayDate;//还款日（date）

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

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public Long getPayDateNum() {
        return payDateNum;
    }

    public void setPayDateNum(Long payDateNum) {
        this.payDateNum = payDateNum;
    }

    public Long getGetMoneyCard() {
        return getMoneyCard;
    }

    public void setGetMoneyCard(Long getMoneyCard) {
        this.getMoneyCard = getMoneyCard;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getBillDateNum() {
        return billDateNum;
    }

    public void setBillDateNum(Long billDateNum) {
        this.billDateNum = billDateNum;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Long getRepayDateNum() {
        return repayDateNum;
    }

    public void setRepayDateNum(Long repayDateNum) {
        this.repayDateNum = repayDateNum;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }
}