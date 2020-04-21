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
    public static Map<String,String> generateCardCode(){

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
}
