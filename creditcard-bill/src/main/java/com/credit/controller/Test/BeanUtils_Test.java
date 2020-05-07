package com.credit.controller.Test;

import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistoryNotEverybill;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils_Test {
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,1);
        int currentYear = c.get(Calendar.YEAR);//当前年份
        int currentMonth = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_MONTH);//当前16日
        System.out.println(currentYear);
        System.out.println(currentMonth);
        System.out.println(currentDay);
        //--------------------------------------------------
        TbHistoryEverybill vo = new TbHistoryEverybill();
        TbHistoryNotEverybill vo1 = new TbHistoryNotEverybill();
        vo1.setId(1L);
        vo1.setBillDate(new Date());
        vo1.setBillDateNum(20200508L);
        vo1.setCcId(1111111111111111L);
        vo1.setGetMoneyCard(2222222222222222L);
        vo1.setMoneyType("人民币");
        vo1.setPayAmount(1000L);
        vo1.setPayInfo("bibiji");
        vo1.setPayDate(new Date());
        vo1.setPayDateNum(20200508L);
        try {
            BeanUtils.copyProperties(vo,vo1);
            System.out.println(vo.getId());
            System.out.println(vo.getMoneyType());
            System.out.println(vo.getCcId());
            System.out.println(vo.getBillDate());
            System.out.println(vo.getBillDateNum());
            System.out.println(vo.getGetMoneyCard());
            System.out.println(vo.getPayAmount());
            System.out.println(vo.getPayDate());
            System.out.println(vo.getPayInfo());
            System.out.println(vo.getPayDateNum());
            System.out.println(vo.getRepayDateNum());
            System.out.println(vo.getRepayDate());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //-----------------------------------------------
        Map<String, Object> map = new HashMap<>();
        map.put("d",123);
        map.put("d",456);
        map.put("d",789);
        System.out.println(map.get("d"));

    }
}
