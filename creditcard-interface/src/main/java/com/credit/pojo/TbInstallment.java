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
    private Integer id;//自增id

    private Integer ccId;//信用卡卡号

    private Integer instaStatus;//分期状态（1为正在进行，2为已结束，3为异常）

    private Integer instaType;//分期类型

    private Integer instaAmount;//分期金额

    private Integer instaTotal;//分期总期数（3,6,12,24）

    private Integer instaCurr;//当前期数

    private Integer instaTotalPricipal;//分期总本金

    private Integer instaTotalInserest;//分期总利息

    private Integer currPricipal;//当月本金

    private Integer currInserest;//当月利息

    private Date instaDate;//分期时间

    private Integer billDateNum;//账单日（纯数字：20200316）

    private Integer repayDateNum;//还款日（纯数字：20200404）

    private Date billDate;//账单日（date）

    private Date repayDate;//还款日（date）

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

    public Integer getInstaStatus() {
        return instaStatus;
    }

    public void setInstaStatus(Integer instaStatus) {
        this.instaStatus = instaStatus;
    }

    public Integer getInstaType() {
        return instaType;
    }

    public void setInstaType(Integer instaType) {
        this.instaType = instaType;
    }

    public Integer getInstaAmount() {
        return instaAmount;
    }

    public void setInstaAmount(Integer instaAmount) {
        this.instaAmount = instaAmount;
    }

    public Integer getInstaTotal() {
        return instaTotal;
    }

    public void setInstaTotal(Integer instaTotal) {
        this.instaTotal = instaTotal;
    }

    public Integer getInstaCurr() {
        return instaCurr;
    }

    public void setInstaCurr(Integer instaCurr) {
        this.instaCurr = instaCurr;
    }

    public Integer getInstaTotalPricipal() {
        return instaTotalPricipal;
    }

    public void setInstaTotalPricipal(Integer instaTotalPricipal) {
        this.instaTotalPricipal = instaTotalPricipal;
    }

    public Integer getInstaTotalInserest() {
        return instaTotalInserest;
    }

    public void setInstaTotalInserest(Integer instaTotalInserest) {
        this.instaTotalInserest = instaTotalInserest;
    }

    public Integer getCurrPricipal() {
        return currPricipal;
    }

    public void setCurrPricipal(Integer currPricipal) {
        this.currPricipal = currPricipal;
    }

    public Integer getCurrInserest() {
        return currInserest;
    }

    public void setCurrInserest(Integer currInserest) {
        this.currInserest = currInserest;
    }

    public Date getInstaDate() {
        return instaDate;
    }

    public void setInstaDate(Date instaDate) {
        this.instaDate = instaDate;
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
}