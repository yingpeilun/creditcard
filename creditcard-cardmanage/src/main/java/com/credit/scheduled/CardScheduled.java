package com.credit.scheduled;

import com.credit.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class CardScheduled {

    @Autowired
    private FunctionService functionService;


    /**
     * 定时更新信用卡信息
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
    public void updateCardDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);//去掉一天
        Date calendarTime = calendar.getTime();
        String newFormat = sdf.format(calendarTime);//当天

        Long billDate = this.functionService.queryBillDate(newFormat);//账单日可能是空的

        this.functionService.scheduledUpdateCard(newFormat, billDate);
    }


    /**
     * 定时更新信用卡利息，滞纳金信息
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void updateCardInt() {
        this.functionService.scheduledUpdateInt();
    }
}
