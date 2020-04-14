package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_credit_card_info")
public class TbCreditCardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//自增id

    private Integer ccId;//信用卡卡号

    private String cardType;//卡片类型

    private String cardName;//卡片名字

    private Integer billDateNum;//账单日（纯数字：20200316）

    private Integer repayDateNum;//还款日（纯数字：20200404）

    private Date billDate;//账单日（date）

    private Date repayDate;//还款日（date）

    private Integer creditAmount;//信用总额度

    private Integer remainAmount;//剩余额度

    private Integer conAmount;//消费额度

    private Integer cashTotalAmount;//取消总额度

    private Integer remainCash;//剩余取现额度

    private Integer cashAmount;//已取现额度

    private Integer repaidAmount;//需还款金额

    private Integer minpaidAmount;//最小还款金额

    private Integer interest;//利息

    private Integer interestRate;//利率，使用中乘利率后除100

    private Integer lateFee;//滞纳金

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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
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

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Integer remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Integer getConAmount() {
        return conAmount;
    }

    public void setConAmount(Integer conAmount) {
        this.conAmount = conAmount;
    }

    public Integer getCashTotalAmount() {
        return cashTotalAmount;
    }

    public void setCashTotalAmount(Integer cashTotalAmount) {
        this.cashTotalAmount = cashTotalAmount;
    }

    public Integer getRemainCash() {
        return remainCash;
    }

    public void setRemainCash(Integer remainCash) {
        this.remainCash = remainCash;
    }

    public Integer getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Integer cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Integer getRepaidAmount() {
        return repaidAmount;
    }

    public void setRepaidAmount(Integer repaidAmount) {
        this.repaidAmount = repaidAmount;
    }

    public Integer getMinpaidAmount() {
        return minpaidAmount;
    }

    public void setMinpaidAmount(Integer minpaidAmount) {
        this.minpaidAmount = minpaidAmount;
    }

    public Integer getInterest() {
        return interest;
    }

    public void setInterest(Integer interest) {
        this.interest = interest;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getLateFee() {
        return lateFee;
    }

    public void setLateFee(Integer lateFee) {
        this.lateFee = lateFee;
    }
}