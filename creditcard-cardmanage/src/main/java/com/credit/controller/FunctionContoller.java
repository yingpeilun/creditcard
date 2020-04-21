package com.credit.controller;

import com.credit.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class FunctionContoller {

    @Autowired
    private FunctionService functionService;



    /**
     * 信用卡消费
     * @param ccId 信用卡卡号
     * @param money 消费金额
     * @param payInfo 消费描述
     * @param getMoneyCardId 收款账号
     * @param moneyType 币种
     * @return Map
     */
    @PostMapping("consume")
    public ResponseEntity<Map<String,Object>> consume(
            @RequestParam("ccId") Long ccId,
            @RequestParam("money")Long money,
            @RequestParam("payInfo")String payInfo,
            @RequestParam("getMoneyCardId")String getMoneyCardId,
            @RequestParam("moneyType")String moneyType
    ){
        Map<String, Object> map = this.functionService.consume(ccId, money, payInfo, getMoneyCardId, moneyType);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(map);
    }
}
