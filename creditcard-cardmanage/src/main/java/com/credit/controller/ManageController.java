package com.credit.controller;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ManageController {

    @Autowired
    private ManageService manageService;

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone) {
        Boolean boo = this.manageService.sendVerifyCode(phone);
        if(!boo){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 查询客户名下的信用卡
     * @param uid
     * @return
     */
    public ResponseEntity<List<TbCreditCardInfo>>
    queryContactCardByUid(@RequestParam("uid") Long uid){
        List<TbCreditCardInfo>list=this.manageService.queryContactCreditCard(uid);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 修改信用卡支付密码
     * @param oldpaypwd 旧密码
     * @param newpaypwd 新密码
     * @param creditCardId 卡号
     * @return
     */
    public ResponseEntity<Map<String,Object>>alterCardPayPassword(
            @RequestParam("oldpaypwd")String oldpaypwd,
            @RequestParam("newpaypwd")String newpaypwd,
            @RequestParam("creditCardId")Long creditCardId
    ){
        Map<String,Object> map=this.manageService.alterCardPayPassword(oldpaypwd,newpaypwd,creditCardId);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(map);
    }


    /**
     * 重置信用卡支付密码
     * @param creditCardId
     * @param securityCode
     * @param valCode
     * @param smsCode
     * @param newPayPwd
     * @return
     */
    public ResponseEntity<Map<String,Object>>resetCardPayPassword(
            @RequestParam("creditCardId")Long creditCardId,
            @RequestParam("creditCardSecurityCode")Long securityCode,
            @RequestParam("valCode")Long valCode,
            @RequestParam("smsCode")String smsCode,
            @RequestParam("newPayPwd") String newPayPwd
    ){
        Map<String,Object> map=this.manageService.resetCardPayPassword(
                creditCardId,securityCode,valCode,smsCode,newPayPwd);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(map);
    }


    /**
     * 根据卡号或者身份证查找名下的卡片（卡片安全信息表）
     * @param creditCardId
     * @param idcard
     * @return
     */
    public ResponseEntity<List<TbCreditCardSecurityInfo>>queryContactCardByCIDorIDC(
            @RequestParam(value = "creditCardId",required = false )Long creditCardId,
            @RequestParam(value = "idcard",required = false ) String idcard
    ){
        List<TbCreditCardSecurityInfo>list=this.manageService.queryContactCardByCIDorIDC(creditCardId,idcard);
        if(CollectionUtils.isEmpty(list)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }


    /**
     * 调整信用卡额度
     * @param creditCardId
     * @param creditAmount
     * @return
     */
    public ResponseEntity<Map<String,Object>>alterCreditAmount(
            @RequestParam(value = "creditCardId")Long creditCardId,
            @RequestParam(value = "creditAmount") Long creditAmount
    ){
        Map<String,Object> map=this.manageService.alterCreditAmount(creditCardId,creditAmount);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(map);
    }
}
