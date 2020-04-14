package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_app_card_status")
public class TbAppCardStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//自增id

    private Integer uid;//用户uid

    private String cardType;//片卡类型

    private String cardName;//卡片名称

    private Integer appStatus;//申请中的状态码（1为1级员工查看，2为2级员工查看，3为3级员工查看）

    private Integer cardStatus;//卡片状态（1为已申请，2为未通过，3为已通过，4制卡中，5为寄送中，6已签收）

    private String logisticeInfo;//物流信息

    private Integer cradId;//卡号（通过申请后才会有）

    private Date appTime;//申请时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public Integer getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Integer cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getLogisticeInfo() {
        return logisticeInfo;
    }

    public void setLogisticeInfo(String logisticeInfo) {
        this.logisticeInfo = logisticeInfo == null ? null : logisticeInfo.trim();
    }

    public Integer getCradId() {
        return cradId;
    }

    public void setCradId(Integer cradId) {
        this.cradId = cradId;
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }
}