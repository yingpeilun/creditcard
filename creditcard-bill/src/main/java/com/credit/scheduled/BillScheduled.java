package com.credit.scheduled;

import com.credit.service.NotBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class BillScheduled {

    @Autowired
    private NotBillService notBillService;

    @Scheduled(cron ="0 0 0 16 * ?" )//每月 16 日 零 时执行一次
    public void updateNotEveryBilltoMonthBill(){
        //1. 通过 s(上个月账单日+1)、 p（当月还款日-1）、ccid 卡号 统计 所有卡的月账 （insert入MonthBill）
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);//当前年份
        int currentMonth = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_MONTH);//当前16日
        String dangmonth = getNum(currentMonth);//（处理数字，当前月份格式：两位数）
        String currentBillDate = currentYear +""+ dangmonth + "" + currentDay;//（当月账单日）
        //计算上一月
        int shangMonth = currentMonth - 1;
        if(shangMonth <= 0){
            shangMonth = 12;
            currentYear -= 1;
        }
        String shangmonth = getNum(shangMonth);//（处理数字，当前月份格式：两位数）
        String shangBillDate = currentYear +""+ shangmonth +"17";//（已结的上个月账单日+1）
        String zuijinPayDate = getZuijinRepayDate();//（最近还款日）
        /********************* （已结的上个月账单日+1）—至— （未结的当月账单日）*****************************/
        //把这个时间段的每一笔账单 放入 【已出账单（每一笔的）】，并统计，统计月结果 放入 【已出账单（每月的）】









    }

    /**
     * 获取最近还款日
     * @return
     */
    private String getZuijinRepayDate() {
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);//当前年份
        int Month = (c.get(Calendar.MONTH)+1);//当前月份
        int Day = c.get(Calendar.DAY_OF_MONTH);//当前日份
        if (Day > 4){
            Month += 1;
            if(Month >= 13){
                Month = 12;
                Year += 1;
            }
        }
        String zuijinmonth = getNum(Month);//（处理数字，格式：两位数）
        return Year +""+ zuijinmonth +"04";//最近还款日
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
