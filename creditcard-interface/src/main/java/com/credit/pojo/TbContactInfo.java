package com.credit.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_contact_info")
public class TbContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//自增id

    private Long uid;//用户uid

    private String contactName;//联系人姓名

    private String contactPhone;//联系人电话

    private Long reWithApplicant;//与申请人关系（1为父子，2为母子，3为夫妻，4为亲戚，5为朋友，6为同事）

    private String email;//电子邮箱

    private Long getCardMethod;//卡片领取方式（1为挂号信邮寄，2银行网点自取）

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Long getReWithApplicant() {
        return reWithApplicant;
    }

    public void setReWithApplicant(Long reWithApplicant) {
        this.reWithApplicant = reWithApplicant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Long getGetCardMethod() {
        return getCardMethod;
    }

    public void setGetCardMethod(Long getCardMethod) {
        this.getCardMethod = getCardMethod;
    }
}