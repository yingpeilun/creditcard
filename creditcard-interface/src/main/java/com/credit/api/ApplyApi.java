package com.credit.api;

import com.credit.bo.PageResult;
import com.credit.pojo.TbAppCardStatus;
import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ApplyApi {

    /**
     * 新增基本信息
     * @param basicInfo
     * @return
     */
    @PostMapping("insertBasic")
    public Boolean insertBasic(TbBasicInfo basicInfo);

    /**
     * 新增公司信息
     * @param companyInfo
     * @return
     */
    @PostMapping("insertCompany")
    public Boolean insertCompany(TbCompanyInfo companyInfo);

    /**
     * 新增联系人信息
     * @param contactInfo
     * @return
     */
    @PostMapping("insertContact")
    public Boolean insertContact(TbContactInfo contactInfo);

    /**
     * 查询卡片状态
     * @param uid
     * @param cardType
     * @return
     */
    @GetMapping("queryCardStatus")
    public String queryCardStatus(
            @RequestParam("uid") Long uid,
            @RequestParam("cardType")String cardType);


    /**
     * 分页工作人员审核申请卡片
     * @param page
     * @param rows
     * @param grade
     * @return
     */
    @GetMapping("queryAppStatus")
    public PageResult<TbAppCardStatus> queryAppStatus(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam("grade") Integer grade);

    /**
     * 根据uid和cardtype来修改卡片目前的审批阶段
     * @param grade
     * @param uid
     * @param cardType
     * @return
     */
    @GetMapping("alterAppStatus")
    public Boolean alterAppStatus(
            @RequestParam("grade") Long grade,
            @RequestParam("uid") Long uid,
            @RequestParam("cardType")String cardType);

    /**
     * 根据uid和cardtype来修改卡片状态为审批不通过
     * @param uid
     * @param cardType
     * @return
     */
    @GetMapping("alterCardStatus")
    public Boolean alterCardStatus(
            @RequestParam("uid") Long uid,
            @RequestParam("cardType")String cardType);
}
