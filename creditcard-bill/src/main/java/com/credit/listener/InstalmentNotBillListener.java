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

    //还款的
//    msg.put("bankName",bankName);//银行名
//        msg.put("cuName",cuName);//户头名
//        msg.put("repayMoney",repayMoney.toString());//还款金额
//        msg.put("ccId",ccId.toString());//信用卡卡号
//        msg.put("scId",scId.toString());//储蓄卡卡号
//    this.rabbitTemplate.convertAndSend
//            ("creditCard.repay.exchange","repay.money.msg",msg);
    //监听分期账单
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.instalmentBill.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.bill.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "instalment.bill.msg"
    ))
    public void ListenInstalmentNotBill(Map<String,String> msg){

    }

    //分期的
//    msg.put("ccid",ccid.toString());//卡号
//            msg.put("payInfo","分期");//交易描述
//            msg.put("moneyType","人民币");//币种
//    Long c=Long.valueOf(everyMoney)*num;
//            msg.put("payAmount",c.toString());//交易总金额(分期总本金)
//            msg.put("payDateNum",sdf.format(d));//交易日期（纯数字）
//            msg.put("getMoneyCard","中信银行");//收款账号
//            msg.put("billDateNum",dateformat1);//账单日(纯数字)
//    // ==>
//            msg.put("InstaTotal",String.valueOf(num));//分期总期数
//            msg.put("CurrPricipal",everyMoney);//每月本金
//            msg.put("CurrInserest",instalMoney);//每月利息
//
//            this.rabbitTemplate.convertAndSend("creditCard.bill.exchange","instalment.bill.msg",msg);
//            this.rabbitTemplate.convertAndSend("creditCard.manage.exchange","instalment.money.msg",msg);
//            return true;
}
