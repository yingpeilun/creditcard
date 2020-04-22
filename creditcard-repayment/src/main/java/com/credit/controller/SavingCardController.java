package com.credit.controller;

import com.credit.service.SavingCardService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class SavingCardController {

    @Autowired
    private SavingCardService savingCardService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 信用卡还款
     * @param ccId 信用卡卡号
     * @param scId 储蓄卡卡号
     * @param money 还款金额
     * @return map
     */
    @PostMapping("repayMoney")
    public ResponseEntity<Map<String,String>> repayMoney(
            @RequestParam("ccId") Long ccId,
            @RequestParam("scId") Long scId,
            @RequestParam("money") Long money
    ){
        Map<String,String>map=this.savingCardService.repayMoney(ccId,scId,money);
        if(CollectionUtils.isEmpty(map)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(map);
    }
}
