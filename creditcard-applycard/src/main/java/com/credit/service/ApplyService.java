package com.credit.service;

import com.credit.bo.PageResult;
import com.credit.mapper.AppCardMapper;
import com.credit.mapper.BasicInfoMapper;
import com.credit.mapper.CompanyInfoMapper;
import com.credit.mapper.ContactInfoMapper;
import com.credit.pojo.TbAppCardStatus;
import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ApplyService {

    @Autowired
    private BasicInfoMapper basicInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Autowired
    private AppCardMapper appCardMapper;

    /**
     * 新增基本信息
     * @param basicInfo
     * @return
     */
    public Boolean insertBasic(TbBasicInfo basicInfo) {
        return this.basicInfoMapper.insert(basicInfo)!=0;
    }

    /**
     * 新增公司信息
     * @param companyInfo
     * @return
     */
    public Boolean insertCompany(TbCompanyInfo companyInfo) {
        return this.companyInfoMapper.insert(companyInfo)!=0;
    }

    /**
     * 新增联系人信息
     * @param contactInfo
     * @return
     */
    public Boolean insertContact(TbContactInfo contactInfo) {
        return this.contactInfoMapper.insert(contactInfo)!=0;
    }

    /**
     * 查询卡片状态
     * @param uid
     * @param cardType
     * @info 1为已申请，2为未通过，3为已通过，4制卡中，5为寄送中，6已签收
     * @return
     */
    public String queryCardStatus(Long uid, String cardType) {
        TbAppCardStatus app=new TbAppCardStatus();
        app.setCardType(cardType);
        app.setUid(uid);
        TbAppCardStatus appCardStatus = this.appCardMapper.selectOne(app);
        Integer cardStatus = appCardStatus.getCardStatus().intValue();
        String msg="";
        switch (cardStatus){
            case 1:
                msg="已申请";
                break;
            case 2:
                msg="未通过";
                break;
            case 3:
                msg="已通过";
                break;
            case 4:
                msg="制卡中";
                break;
            case 5:
                msg="寄送中";
                break;
            case 6:
                msg="已签收";
                break;
        }
        return msg;
    }

    /**
     * 工作人员审批申请卡片的分页
     * @info: 申请中的状态码（1为1级员工查看，2为2级员工查看，3为3级员工查看）
     * @param page
     * @param rows
     * @param grade
     * @return
     */
    public PageResult<TbAppCardStatus> queryAppStatus(Integer page, Integer rows, Integer grade) {
        Example example=new Example(TbAppCardStatus.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("app_status",grade);
        example.setOrderByClause("`app_time` asc");

        List<TbAppCardStatus> list = this.appCardMapper.selectByExample(example);
        PageInfo<TbAppCardStatus> pageInfo=new PageInfo<>(list);

        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),pageInfo.getList());
    }

    /**
     * 根据uid和cardtype来修改卡片目前的审批阶段
     * @param grade
     * @param uid
     * @param cardType
     * @return
     */
    public Boolean alterAppStatus(Long grade, Long uid, String cardType) {
        Example example=new Example(TbAppCardStatus.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid).andEqualTo("card_type",cardType);
        TbAppCardStatus app=new TbAppCardStatus();
        app.setAppStatus(grade+1);

        return this.appCardMapper.updateByExampleSelective(app, example)!=0;
    }

    /**
     * 根据uid和cardtype来修改卡片状态为审批不通过
     * @param uid
     * @param cardType
     * @info 1为已申请，2为未通过，3为已通过，4制卡中，5为寄送中，6已签收
     * @return
     */
    public Boolean alterCardStatus(Long uid, String cardType) {
        Example example=new Example(TbAppCardStatus.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid).andEqualTo("card_type",cardType);
        TbAppCardStatus app=new TbAppCardStatus();
        app.setCardStatus(2L);

        return this.appCardMapper.updateByExampleSelective(app, example)!=0;
    }
}
