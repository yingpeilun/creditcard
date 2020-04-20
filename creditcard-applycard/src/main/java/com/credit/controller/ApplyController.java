package com.credit.controller;

import com.credit.bo.PageResult;
import com.credit.pojo.TbAppCardStatus;
import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import com.credit.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    /**
     * 新增基本信息
     * @param basicInfo
     * @return
     */
    @PostMapping("insertBasic")
    public ResponseEntity<Boolean> updateBasic(TbBasicInfo basicInfo){
        Boolean boo = this.applyService.insertBasic(basicInfo);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 新增公司信息
     * @param companyInfo
     * @return
     */
    @PostMapping("insertCompany")
    public ResponseEntity<Boolean> insertCompany(TbCompanyInfo companyInfo){
        Boolean boo = this.applyService.insertCompany(companyInfo);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 新增联系人信息
     * @param contactInfo
     * @return
     */
    @PostMapping("insertContact")
    public ResponseEntity<Boolean> insertContact(TbContactInfo contactInfo){
        Boolean boo = this.applyService.insertContact(contactInfo);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 查询卡片状态
     * @param uid
     * @param cardType
     * @return
     */
    @GetMapping("queryCardStatus")
    public ResponseEntity<String> queryCardStatus(
            @RequestParam("uid") Long uid,
            @RequestParam("cardType")String cardType){
        String msg=this.applyService.queryCardStatus(uid,cardType);
        return ResponseEntity.ok(msg);
    }


    /**
     * 分页工作人员审核申请卡片
     * @param page
     * @param rows
     * @param grade
     * @return
     */
    @GetMapping("queryAppStatus")
    public ResponseEntity<PageResult<TbAppCardStatus>> queryAppStatus(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam("grade") Integer grade
    ){
        PageResult<TbAppCardStatus>appPage=this.applyService.queryAppStatus(page,rows,grade);
        if(CollectionUtils.isEmpty(appPage.getItems())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appPage);
    }

    /**
     * 根据uid和cardtype来修改卡片目前的审批阶段
     * @param grade
     * @param uid
     * @param cardType
     * @return
     */
    @GetMapping("alterAppStatus")
    public ResponseEntity<Boolean>alterAppStatus(
            @RequestParam("grade") Long grade,
            @RequestParam("uid") Long uid,
            @RequestParam("cardType")String cardType
    ){
        Boolean boo=this.applyService.alterAppStatus(grade,uid,cardType);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 根据uid和cardtype来修改卡片状态为审批不通过
     * @param uid
     * @param cardType
     * @return
     */
    @GetMapping("alterCardStatus")
    public ResponseEntity<Boolean>alterCardStatus(
            @RequestParam("uid") Long uid,
            @RequestParam("cardType")String cardType
    ){
        Boolean boo=this.applyService.alterCardStatus(uid,cardType);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }
}
