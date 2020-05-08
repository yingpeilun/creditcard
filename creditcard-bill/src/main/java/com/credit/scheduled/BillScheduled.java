package com.credit.scheduled;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.pojo.TbHistorylMonthbill;
import com.credit.service.BillService;
import com.credit.service.ClientService;
import com.credit.service.NotBillService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BillScheduled {

    @Autowired
    private NotBillService notBillService;
    @Autowired
    private BillService billService;
    @Autowired
    private ClientService clientService;

    @Scheduled(cron ="0 0 0 16 * ?" )//每月 16 日 零 时执行一次
    public void updateNotEveryBilltoMonthBill(){
        //1. 通过 s(上个月账单日+1)、 p（当月还款日-1）、ccid 卡号 统计 所有卡的月账 （insert入MonthBill）
        Calendar c = Calendar.getInstance();
        //计算现在当月
        int currentYear = c.get(Calendar.YEAR);//当前年份
        int currentMonth = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_MONTH);//当前16日
        String dangmonth = getNum(currentMonth);//（处理数字，当前月份格式：两位数）
        String currentBillDate = currentYear +""+ dangmonth + "" + currentDay;//（现在结的当月账单日）(String)
        Date currentBillDate_Date = StringToDate(currentBillDate);
        Long currentBillDate_Long = Long.valueOf(currentBillDate);
        //计算上一月账单日
        String shangBillDate = getShangBillDate(currentYear, currentMonth);//（已结的上个月账单日+1）(String)
        Date shangBillDate_date = StringToDate(shangBillDate);
        Long shangBillDate_long = Long.valueOf(shangBillDate);
        //计算下个月账单日
       /* String xiaBillDate = getXiaBillDate(currentYear, currentMonth);//（未结的下个月账单日）(String)
        Date xiaBillDate_date = StringToDate(xiaBillDate);
        Long xiaBillDate_long = Long.valueOf(xiaBillDate);*/
        //计算下个月还款日
        String zuijinPayDate = getZuijinRepayDate(c);//（最近还款日）(String)
        Date zuijinPayDate_date = StringToDate(zuijinPayDate);//（最近还款日）(Date)
        Long zuijinPayDate_long = Long.valueOf(zuijinPayDate);//（最近还款日）(Long)

        /********************* （已结的上个月账单日+1） 至 （未结的当月账单日）*****************************/
        // 把这个时间段的每一笔账单 放入 【已出账单(每一笔的)】，并统计每张卡的月结果，统计月结果 放入 【已出账单（每月的）】
        //（当月账单日、最近还款日）按账单日、还款日放入【已出账单（每月的）】；
        // 计算当月消费总金额 并放入 【已出账单（每月的）】

        /* ~~~~~~~~1. 查询时间段内的每一笔账单~~~~~~~~~ */
        Map<String, Object> map = new HashMap<>();
        map.put("s",shangBillDate);
        map.put("p",currentBillDate);
        List<TbHistoryNotEverybill> OneMonthNotBillList = notBillService.getOneMonthEveryNotbillHistory(map);
        /* ~~~~~~~~2. 将时间没的每笔账单 放入 【已出账单(每一笔的)】~~~~~~~~ */
        for (int i = 0; i < OneMonthNotBillList.size(); i++ ){
            TbHistoryNotEverybill vo = OneMonthNotBillList.get(i);//获取一笔未出账单
            TbHistoryEverybill jo = new TbHistoryEverybill();//创建一个JavaBean对象(TbHistoryEverybill),为了装成一笔已出账单
            try {
                BeanUtils.copyProperties(jo,vo);
                jo.setRepayDate(zuijinPayDate_date);//还款日（Date）
                jo.setRepayDateNum(zuijinPayDate_long);//还款日（Long）
                /* ~~~~~~~~~ 添加入一条已出账单 ~~~~~~~~~ */
                boolean b = billService.inputOneBill(jo);
                System.out.println("添加第"+(i+1)+"次一笔已出的账单: "+b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        /* ~~~~~~~~~3. 找出所有卡~~~~~~~~~ */
        List<TbCreditCardInfo> creditCardInfosList = clientService.findallTbCreditCardInfo();
        /* ~~~~~~~~~4. 统计每张卡的月账单结果~~~~~~~~~ */
        Map<String, Object> map_2 = new HashMap<>();
        map_2.put("ss",shangBillDate);
        map_2.put("s",currentBillDate);
        Long pay_sum;
        for (int i = 0; i < creditCardInfosList.size(); i++ ){
            Long id = creditCardInfosList.get(i).getId();
            Long ccId = creditCardInfosList.get(i).getCcId();
            map_2.put("ccid",ccId);
            pay_sum = 0L;
            /* ~~~~~~~~~~按序查寻这段时间内的一张卡的所有交易账单~~~~~~~~~ */
            List<TbHistoryEverybill> everybillccidlist = billService.getOneMonthEverybillHistory(map);
            /* ~~~~~~~~~统计交易总金额~~~~~~~~ */
            for (int j = 0; j < everybillccidlist.size(); j++ ){
                TbHistoryEverybill vo_1 = everybillccidlist.get(j);
                Long onePayAmount = vo_1.getPayAmount();
                pay_sum += onePayAmount;
            }
            TbHistorylMonthbill monthbill = new TbHistorylMonthbill();
            monthbill.setCcId(ccId);
            monthbill.setCurrConsumption(pay_sum);//某卡当月消费总金额（某卡当月需还款总金额）
            monthbill.setRepayDate(zuijinPayDate_date);
            monthbill.setRepayDateNum(zuijinPayDate_long);
            monthbill.setBillDate(currentBillDate_Date);
            monthbill.setBillDateNum(currentBillDate_Long);
            monthbill.setMoneyType("人民币");
            monthbill.setCashAmount(0L);//取现金额
            monthbill.setCurrRepaid(0L);//当月还款金额（当月已还款金额）
            /* ~~~~~~~~~~5. 每张卡的月账单结果 添加到 已出月账单表~~~~~~~~~ */
            boolean b = billService.inputMonthBill(monthbill);
            System.out.println("添加已结好的月账单到已出月账单表:"+b);
            /* ~~~~~~~~~统计每张卡的需还款金额总和~~~~~~~~~ */
            //【某卡片所有月的已出月账单的List集合】
            /*List<TbHistorylMonthbill> cardMonthBillList = billService.getCardMonthBillList(ccId);
            Long consumptionSum = 0L;//当月应还款
            Long repaiyAmountSum = 0L;//当月已还款
            for (int p = 0; p < cardMonthBillList.size(); p++ ){
                Long currConsumption = cardMonthBillList.get(p).getCurrConsumption();//一个月的消费额
                consumptionSum += currConsumption;
                Long currRepaid = cardMonthBillList.get(p).getCurrRepaid();//一个月的已还款额
                repaiyAmountSum += currRepaid;
            }
            Long renxuRepaiySum = consumptionSum - repaiyAmountSum //仍需还款总金额*/
            /* ~~~~~~~~~更新信用卡信息表的账单日、还款日等~~~~~~~~~ */
            TbCreditCardInfo qo = new TbCreditCardInfo();
            qo.setId(id);
            qo.setCashAmount(0L);//已取现金额
            qo.setRepaidAmount(pay_sum);//仍需还款总金额
            qo.setConAmount(pay_sum);//消费总金额(需还款总金额)
            qo.setBillDate(currentBillDate_Date);//账单日
            qo.setBillDateNum(currentBillDate_Long);//账单日(纯数字)
            qo.setRepayDate(zuijinPayDate_date);
            qo.setRepayDateNum(zuijinPayDate_long);
            boolean b1 = clientService.updateBillDateAndRepayDate(qo);
            System.out.println("更新信用卡信息（账单日、还款日）:"+b1);
        }
    }

    /**
     * （未结的下个月账单日）(String)
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    private String getXiaBillDate(int currentYear, int currentMonth) {
        int currentYear_1 =  currentYear;
        int xiaMonth = currentMonth + 1;
        if(xiaMonth >= 13){
            xiaMonth = 1;
            currentYear_1 += 1;
        }
        String xiamonth = getNum(xiaMonth);//（处理数字，当前月份格式：两位数）
        return currentYear_1 +""+ xiamonth +"16";
    }
    /**
     * （已结的上个月账单日+1）(String)
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    private String getShangBillDate(int currentYear, int currentMonth) {
        int currentYear_1 =  currentYear;
        int shangMonth = currentMonth - 1;
        if(shangMonth <= 0){
            shangMonth = 12;
            currentYear_1 -= 1;
        }
        String shangmonth = getNum(shangMonth);//（处理数字，当前月份格式：两位数）
        return currentYear_1 +""+ shangmonth +"17";
    }

    /**
     * 获取最近还款日
     * @param c 当前日历
     * @return
     */
    private String getZuijinRepayDate(Calendar c) {
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
