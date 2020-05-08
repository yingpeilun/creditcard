package com.credit.listener;

import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.service.BillService;
import com.credit.service.NotBillService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class InstalmentNotBillListener {

    @Autowired
    private NotBillService notBillService;
    @Autowired
    private BillService billService;

    //分期的
//            msg.put("ccid",ccid.toString());//卡号
//            msg.put("payInfo","分期");//交易描述
//            msg.put("moneyType","人民币");//币种
//              Long c=Long.valueOf(everyMoney)*num;
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
    //监听分期账单
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.instalmentBill.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.bill.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "instalment.bill.msg"
    ))
    public void ListenInstalmentNotBill(Map<String,String> msg){
        String InstaTotal = msg.get("InstaTotal");//分期数
        Integer num = Integer.valueOf(InstaTotal);//分期数(Long)
        String ccid = msg.get("ccid");//卡号
        Long ccId = Long.valueOf(ccid);//卡号（Long）
        String payInfo = msg.get("payInfo");//交易描述
        String moneyType = msg.get("moneyType");//人民币

        String payDateNum = msg.get("payDateNum");//交易时间
        Long payDateNum_long = Long.valueOf(payDateNum);//交易时间（Long）
        Date payDateNum_Date = StringToDate(payDateNum);//交易时间（Date）

        List<Date> list1 = new ArrayList<>();
        List<Long> list2 =new ArrayList<>();
        String yyyy = payDateNum.substring(4);
        String MM = payDateNum.substring(4, 6);
        String dd = payDateNum.substring(6);
        int yue = 0;
        int nian = Integer.valueOf(yyyy);
        if (MM.substring(0,1).equals("0")){
            yue = Integer.valueOf(MM.substring(1, 2));
        }else {
            yue = Integer.valueOf(MM.substring(0,2));
        }
        for (int i = 0 ; i < num ;i++) {
            yue += 1;
            if (yue >= 13) {
                yue = 1;
                nian += 1;
            }
            String month = getNum(yue);
            String d1 = nian + month + dd;
            Date date = StringToDate(d1);
            Long aLong = Long.valueOf(d1);
            list1.add(date);
            list2.add(aLong);
        }

        String billDateNum = msg.get("billDateNum");//账单日
        Long billDateNum_long = Long.valueOf(billDateNum);//账单日(Long)
        Date billDateNum_date = StringToDate(billDateNum);//账单日（Date）

        List<Date> list4 = new ArrayList<>();
        List<Long> list5 =new ArrayList<>();
        String y = billDateNum.substring(4);
        String m = billDateNum.substring(4, 6);
        String d = billDateNum.substring(6);
        int yue1 = 0;
        int nian1 = Integer.valueOf(y);
        if (m.substring(0,1).equals("0")){
            yue1 = Integer.valueOf(m.substring(1, 2));
        }else {
            yue1 = Integer.valueOf(m.substring(0,2));
        }
        for (int i = 0 ; i < num ;i++) {
            yue1 += 1;
            if (yue1 >= 13) {
                yue1 = 1;
                nian1 += 1;
            }
            String month1 = getNum(yue1);
            String d2 = nian1 + month1 + d;
            Date date2 = StringToDate(d2);
            Long aLong2 = Long.valueOf(d2);
            list4.add(date2);
            list5.add(aLong2);
        }
        String getMoneyCard = msg.get("getMoneyCard");//收款账号
        Long getMoneyCard_long = Long.valueOf(getMoneyCard);//收款账号(Long)
        String CurrPricipal = msg.get("CurrPricipal");//每一期本金
        Long CurrPricipal_Long = Long.valueOf(CurrPricipal);//每一期本金(Long)
        String CurrInserest = msg.get("CurrInserest");//每一期利息
        Long CurrInserest_Long = Long.valueOf(CurrInserest);//每一期利息(Long)
        Long PayAmount = CurrPricipal_Long + CurrInserest_Long;

        for (int i = -1 ; i < num - 1 ;i++) {
            TbHistoryNotEverybill vo = new TbHistoryNotEverybill();
            if (i== -1){
                vo.setCcId(ccId);
                vo.setPayInfo(payInfo);
                vo.setMoneyType(moneyType);
                vo.setPayDateNum(payDateNum_long);
                vo.setPayDate(payDateNum_Date);
                vo.setGetMoneyCard(getMoneyCard_long);
                vo.setBillDate(billDateNum_date);
                vo.setBillDateNum(billDateNum_long);
                vo.setPayAmount(PayAmount);
                boolean b = notBillService.insertOneEveryBill(vo);
                System.out.println("插入一笔分期未出账单第" + (i+2) + ":" + b);
            }else {
                vo.setCcId(ccId);
                vo.setPayInfo(payInfo);
                vo.setMoneyType(moneyType);
                vo.setPayDateNum(list2.get(i));
                vo.setPayDate(list1.get(i));
                vo.setGetMoneyCard(getMoneyCard_long);
                vo.setBillDate(list4.get(i));
                vo.setBillDateNum(list5.get(i));
                vo.setPayAmount(PayAmount);
                boolean b = notBillService.insertOneEveryBill(vo);
                System.out.println("插入一笔分期未出账单第" + (i+2) + ":" + b);
            }
        }
    }

    //还款的
//        msg.put("bankName",bankName);//银行名
//        msg.put("cuName",cuName);//户头名
//        msg.put("repayMoney",repayMoney.toString());//还款金额
//        msg.put("ccId",ccId.toString());//信用卡卡号
//        msg.put("scId",scId.toString());//储蓄卡卡号
//    this.rabbitTemplate.convertAndSend
//            ("creditCard.repay.exchange","repay.money.msg",msg);
    //监听还款账单
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "creditCard.repayBill.queue",durable = "true"),
            exchange = @Exchange(value = "creditCard.repay.exchange",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "repay.money.msg"
    ))
    public void ListenRepayBill(Map<String,String> msg){
        String bankName=msg.get("bankName");
        String cuName=msg.get("cuName");
        String repayMoney=msg.get("repayMoney");
        String ccId=msg.get("ccId");
        String scId=msg.get("scId");
        //还款时要考虑在哪个时间段还款的
        //还款成功后，更新已出月账单和信用卡信息
    }






    /**
     * 日期转换：String => java.util.Date
     * @param Date String类型的yyyyMMdd格式的月账单日
     * @return Date
     */
    private Date StringToDate(String Date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 处理日期int前面的无零的问题
     * @param num 原数
     * @return String
     */
    private static String getNum(int num){
        return num > 9 ? "" + num : "0" + num;
    }
}
