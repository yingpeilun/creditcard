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
    private Long id;//自增id

    private Long uid;//用户uid

    private String cardType;//片卡类型

    private String cardName;//卡片名称

    private Long appStatus;//申请中的状态码（1为1级员工查看，2为2级员工查看，3为3级员工查看）

    private Long cardStatus;//卡片状态（1为已申请，2为未通过，3为已通过，4制卡中，5为寄送中，6已签收）

    private String logisticeInfo;//物流信息

    private Long cradId;//卡号（通过申请后才会有）

    private Date appTime;//申请时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
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

    public Long getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Long appStatus) {
        this.appStatus = appStatus;
    }

    public Long getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Long cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getLogisticeInfo() {
        return logisticeInfo;
    }

    public void setLogisticeInfo(String logisticeInfo) {
        this.logisticeInfo = logisticeInfo == null ? null : logisticeInfo.trim();
    }

    public Long getCradId() {
        return cradId;
    }

    public void setCradId(Long cradId) {
        this.cradId = cradId;
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }
}