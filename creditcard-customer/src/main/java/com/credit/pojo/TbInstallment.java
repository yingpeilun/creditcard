package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="tb_installment")
public class TbInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id

    private Long ccId;//信用卡卡号

    private Long instaStatus;//分期状态（1为正在进行，2为已结束，3为异常）

    private Long instaType;//分期类型

    private Long instaAmount;//分期金额

    private Long instaTotal;//分期总期数（3,6,12,24）

    private Long instaCurr;//当前期数

    private Long instaTotalPricipal;//分期总本金

    private Long instaTotalInserest;//分期总利息

    private Long currPricipal;//当月本金

    private Long currInserest;//当月利息

    private Date instaDate;//分期时间

    private Long billDateNum;//账单日（纯数字：20200316）

    private Long repayDateNum;//还款日（纯数字：20200404）

    private Date billDate;//账单日（date）

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

    public Long getInstaStatus() {
        return instaStatus;
    }

    public void setInstaStatus(Long instaStatus) {
        this.instaStatus = instaStatus;
    }

    public Long getInstaType() {
        return instaType;
    }

    public void setInstaType(Long instaType) {
        this.instaType = instaType;
    }

    public Long getInstaAmount() {
        return instaAmount;
    }

    public void setInstaAmount(Long instaAmount) {
        this.instaAmount = instaAmount;
    }

    public Long getInstaTotal() {
        return instaTotal;
    }

    public void setInstaTotal(Long instaTotal) {
        this.instaTotal = instaTotal;
    }

    public Long getInstaCurr() {
        return instaCurr;
    }

    public void setInstaCurr(Long instaCurr) {
        this.instaCurr = instaCurr;
    }

    public Long getInstaTotalPricipal() {
        return instaTotalPricipal;
    }

    public void setInstaTotalPricipal(Long instaTotalPricipal) {
        this.instaTotalPricipal = instaTotalPricipal;
    }

    public Long getInstaTotalInserest() {
        return instaTotalInserest;
    }

    public void setInstaTotalInserest(Long instaTotalInserest) {
        this.instaTotalInserest = instaTotalInserest;
    }

    public Long getCurrPricipal() {
        return currPricipal;
    }

    public void setCurrPricipal(Long currPricipal) {
        this.currPricipal = currPricipal;
    }

    public Long getCurrInserest() {
        return currInserest;
    }

    public void setCurrInserest(Long currInserest) {
        this.currInserest = currInserest;
    }

    public Date getInstaDate() {
        return instaDate;
    }

    public void setInstaDate(Date instaDate) {
        this.instaDate = instaDate;
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
}