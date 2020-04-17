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
    private Long id;//自增id

    private Long ccId;//信用卡卡号

    private String cardType;//卡片类型

    private String cardName;//卡片名字

    private Long billDateNum;//账单日（纯数字：20200316）

    private Long repayDateNum;//还款日（纯数字：20200404）

    private Date billDate;//账单日（date）

    private Date repayDate;//还款日（date）

    private Long creditAmount;//信用总额度

    private Long remainAmount;//剩余额度

    private Long conAmount;//消费额度

    private Long cashTotalAmount;//取消总额度

    private Long remainCash;//剩余取现额度

    private Long cashAmount;//已取现额度

    private Long repaidAmount;//需还款金额

    private Long minpaidAmount;//最小还款金额

    private Long interest;//利息

    private Long interestRate;//利率，使用中乘利率后除100

    private Long lateFee;//滞纳金

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

    public Long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public Long getConAmount() {
        return conAmount;
    }

    public void setConAmount(Long conAmount) {
        this.conAmount = conAmount;
    }

    public Long getCashTotalAmount() {
        return cashTotalAmount;
    }

    public void setCashTotalAmount(Long cashTotalAmount) {
        this.cashTotalAmount = cashTotalAmount;
    }

    public Long getRemainCash() {
        return remainCash;
    }

    public void setRemainCash(Long remainCash) {
        this.remainCash = remainCash;
    }

    public Long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Long getRepaidAmount() {
        return repaidAmount;
    }

    public void setRepaidAmount(Long repaidAmount) {
        this.repaidAmount = repaidAmount;
    }

    public Long getMinpaidAmount() {
        return minpaidAmount;
    }

    public void setMinpaidAmount(Long minpaidAmount) {
        this.minpaidAmount = minpaidAmount;
    }

    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Long interestRate) {
        this.interestRate = interestRate;
    }

    public Long getLateFee() {
        return lateFee;
    }

    public void setLateFee(Long lateFee) {
        this.lateFee = lateFee;
    }
}