package com.credit.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_history_monthbill")
public class TbHistorylMonthbill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id

    private Long ccId;//信用卡卡号

    private Long currConsumption;//当月消费金额

    private Long currRepaid;//当月还款金额

    private Long cashAmount;//取现金额

    private Long billDateNum;//账单日（纯数字：20200316）

    private Long repayDateNum;//还款日（纯数字：20200404）
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date billDate;//账单日(date)
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date repayDate;//还款日(date)

    private String moneyType;//币种

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

    public Long getCurrConsumption() {
        return currConsumption;
    }

    public void setCurrConsumption(Long currConsumption) {
        this.currConsumption = currConsumption;
    }

    public Long getCurrRepaid() {
        return currRepaid;
    }

    public void setCurrRepaid(Long currRepaid) {
        this.currRepaid = currRepaid;
    }

    public Long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Long getBillDateNum() {
        return billDateNum;
    }

    public void setBillDateNum(Long billDateNum) {
        this.billDateNum = billDateNum;
    }

    public Long getRepayDateNum() {
        return repayDateNum;
    }

    public void setRepayDateNum(Long repayDateNum) {
        this.repayDateNum = repayDateNum;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType == null ? null : moneyType.trim();
    }
}