package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_basic_info")
public class TbBasicInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//id自增

    private Long uid;//用户uid

    private Long maritalStatus;//婚姻状况（1为未婚，2为已婚，3为离异，4为丧偶）

    private Long eduLevel;//教育程度（1为初中，2为高中，3为大专，4为本科，5为本科以上））

    private String securityQuestion;//安全问题

    private String securityAnwers;//预留答案

    private String currentAddress;//当前住址

    private Long housingStatus;//住宅情况（1为租房，2为自购房，3为自建房）

    private Long postcode;//邮政编码

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

    public Long getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Long maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Long getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Long eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion == null ? null : securityQuestion.trim();
    }

    public String getSecurityAnwers() {
        return securityAnwers;
    }

    public void setSecurityAnwers(String securityAnwers) {
        this.securityAnwers = securityAnwers == null ? null : securityAnwers.trim();
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress == null ? null : currentAddress.trim();
    }

    public Long getHousingStatus() {
        return housingStatus;
    }

    public void setHousingStatus(Long housingStatus) {
        this.housingStatus = housingStatus;
    }

    public Long getPostcode() {
        return postcode;
    }

    public void setPostcode(Long postcode) {
        this.postcode = postcode;
    }
}