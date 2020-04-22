package com.credit.listener;

import com.credit.service.FunctionService;
import com.credit.service.ManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Listener {

    @Autowired
    private FunctionService functionService;

    @Autowired
    private ManageService manageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.repay.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.repay.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "repay.money.msg"
    ))
    public void ListenSms(Map<String,String> msg){

        String bankName=msg.get("bankName");
        String cuName=msg.get("cuName");
        String repayMoney=msg.get("repayMoney");
        String ccId=msg.get("ccId");
        String scId=msg.get("scId");

        if(StringUtils.isBlank(ccId)||StringUtils.isBlank(repayMoney)){
            return;
        }
        this.functionService.updateCardAmount(ccId,repayMoney);
//        账单需要接受的msg
//        String payInfo=msg.get("payInfo");
//        String getMoneyCardId=msg.get("getMoneyCardId");
//        String moneyType=msg.get("moneyType");
//        String ccId=msg.get("ccId");
//
//        String money=msg.get("money");//消费的列队
//        String interest=msg.get("interest");//利息的列队
//        if(!StringUtils.isBlank(money)){
//            //this.functionService.
//        }else if(!StringUtils.isBlank(interest)){
//            //this.functionService.
//        }

    }
}
