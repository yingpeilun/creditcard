package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_history_monthbill")
public class TbHistorylMonthbill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//自增id

    private Integer ccId;//信用卡卡号

    private Integer currConsumption;//当月消费金额

    private Integer currRepaid;//当月还款金额

    private Integer cashAmount;//取现金额

    private Integer billDateNum;//账单日（纯数字：20200316）

    private Integer repayDateNum;//还款日（纯数字：20200404）

    private Date billDate;//账单日(date)

    private Date repayDate;//还款日(date)

    private String moneyType;//币种

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

    public Integer getCurrConsumption() {
        return currConsumption;
    }

    public void setCurrConsumption(Integer currConsumption) {
        this.currConsumption = currConsumption;
    }

    public Integer getCurrRepaid() {
        return currRepaid;
    }

    public void setCurrRepaid(Integer currRepaid) {
        this.currRepaid = currRepaid;
    }

    public Integer getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Integer cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Integer getBillDateNum() {
        return billDateNum;
    }

    public void setBillDateNum(Integer billDateNum) {
        this.billDateNum = billDateNum;
    }

    public Integer getRepayDateNum() {
        return repayDateNum;
    }

    public void setRepayDateNum(Integer repayDateNum) {
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