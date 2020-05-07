package com.credit.Listener;

import com.credit.SmsUtils.SendSms;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
public class SmsListener {

    @Autowired
    private SendSms sendSms;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.sms.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.sms.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "sms.verify.code"
    ))
    public void ListenSms(Map<String,String> msg){
        String phone = msg.get("phone");
        String code = msg.get("code");
        System.out.println("phone:"+phone);
        System.out.println("code:"+code);
        if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)){
            return;
        }
        this.sendSms.sendSms(phone,code);
    }
}
