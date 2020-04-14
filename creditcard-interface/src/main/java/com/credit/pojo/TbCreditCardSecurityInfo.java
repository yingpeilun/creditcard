package com.credit.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_credit_card_security_info")
public class TbCreditCardSecurityInfo {
    @Id
    private Integer ccId;//信用卡卡号

    private Integer uid;//用户uid

    private Integer cardType;//卡片状态（1为未激活，2为正常，3为异常，4为冻结，5为挂失，6为销户）

    private String holderName;//持卡人姓名

    private String holderPhone;//持卡人电话

    private String idCard;//身份证

    private String paypwd;//支付密码

    private String solt;//用于密码加密

    private Integer valCode;//有效期码（在信用卡的正面）

    private Integer securityCode;//安全码（在信用卡的背面）

    public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName == null ? null : holderName.trim();
    }

    public String getHolderPhone() {
        return holderPhone;
    }

    public void setHolderPhone(String holderPhone) {
        this.holderPhone = holderPhone == null ? null : holderPhone.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPaypwd() {
        return paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd == null ? null : paypwd.trim();
    }

    public String getSolt() {
        return solt;
    }

    public void setSolt(String solt) {
        this.solt = solt == null ? null : solt.trim();
    }

    public Integer getValCode() {
        return valCode;
    }

    public void setValCode(Integer valCode) {
        this.valCode = valCode;
    }

    public Integer getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(Integer securityCode) {
        this.securityCode = securityCode;
    }
}