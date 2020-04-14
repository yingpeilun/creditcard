package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_basic_info")
public class TbBasicInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//id自增

    private Integer uid;//用户uid

    private Integer maritalStatus;//婚姻状况（1为未婚，2为已婚，3为离异，4为丧偶）

    private Integer eduLevel;//教育程度（1为初中，2为高中，3为大专，4为本科，5为本科以上））

    private String securityQuestion;//安全问题

    private String securityAnwers;//预留答案

    private String currentAddress;//当前住址

    private Integer housingStatus;//住宅情况（1为租房，2为自购房，3为自建房）

    private Integer postcode;//邮政编码

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

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
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

    public Integer getHousingStatus() {
        return housingStatus;
    }

    public void setHousingStatus(Integer housingStatus) {
        this.housingStatus = housingStatus;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }
}