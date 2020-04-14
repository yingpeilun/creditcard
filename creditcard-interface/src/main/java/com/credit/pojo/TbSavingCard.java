package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_saving_card")
public class TbSavingCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//自增id

    private Integer ccId;//信用卡卡号

    private Integer scId;//储蓄卡卡号

    private String bankName;//银行名

    private Integer singleQuota;//单笔限额

    private String cuName;//户头名（储蓄卡主人的名字）

    private Integer totalAmount;//余额（用于模拟支付）

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

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Integer getSingleQuota() {
        return singleQuota;
    }

    public void setSingleQuota(Integer singleQuota) {
        this.singleQuota = singleQuota;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName == null ? null : cuName.trim();
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}