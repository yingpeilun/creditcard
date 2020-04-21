package com.credit.listener;

import com.credit.service.FunctionService;
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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.bill.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.bill.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "bill.consume.msg"
    ))
    public void ListenSms(Map<String,String> msg){

        String payInfo=msg.get("payInfo");
        String getMoneyCardId=msg.get("getMoneyCardId");
        String moneyType=msg.get("moneyType");
        String ccId=msg.get("ccId");

        String money=msg.get("money");//消费的列队
        String interest=msg.get("interest");//利息的列队

        if(!StringUtils.isBlank(money)){
            //this.functionService.
        }else if(!StringUtils.isBlank(interest)){
            //this.functionService.
        }

    }
}
