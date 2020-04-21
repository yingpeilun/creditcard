package com.credit.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreditCardUtils {

    /**
     * 生成卡号，安全码，有效期码
     * @return msg
     */
    public Map<String,String> generateCardCode(){

        String code1 = NumberUtils.generateCode(5);
        String code2 = NumberUtils.generateCode(5);

        String creditCardId = "622918"+code1+code2;
        String creditCardSecurityCode = code2.substring(1,5)+code1.substring(1,4);

        DateFormat dateFormat = new SimpleDateFormat("MMdd");
        String valCode = dateFormat.format(new Date());

        Map<String,String>msg=new HashMap<>();
        msg.put("creditCardId",creditCardId);
        msg.put("creditCardSecurityCode",creditCardSecurityCode);
        msg.put("valCode",valCode);

        return msg;
    }


    /**
     * 计算每日的更新利息
     * @param overdueDay 逾期天数
     * @param overdueAmount 逾期金额
     * @param rate 利率
     * @return interest 利息
     */
    public Long mathInterest(Long overdueDay,Long overdueAmount,Long rate){
        return overdueAmount*overdueDay*rate;
    }

    /**
     * 计算每月的滞纳金
     * @param overdueDay 逾期天数
     * @param overdueAmount 逾期金额
     * @param minpayAmount 最低支付金额
     * @return late_fee 滞纳金
     */
    public Long mathLateFee(Long overdueDay,Long overdueAmount,Long minpayAmount){
        return (minpayAmount-overdueAmount)*5;
    }
}

