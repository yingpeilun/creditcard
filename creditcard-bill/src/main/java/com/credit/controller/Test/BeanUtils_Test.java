package com.credit.controller.Test;

import java.util.Calendar;

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

    }
}
