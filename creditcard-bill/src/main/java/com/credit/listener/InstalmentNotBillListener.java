package com.credit.listener;

import com.credit.service.NotBillService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class InstalmentNotBillListener {

    @Autowired
    private NotBillService notBillService;

    //监听还款
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.bill.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.bill.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "instalment.bill.msg"
    ))
    public void ListenInstalmentNotBill(Map<String,String> msg){

    }
}
