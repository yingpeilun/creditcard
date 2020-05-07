package com.credit.controller;

import com.credit.pojo.*;
import com.credit.service.BillFeignClient;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 已出账单查询
 */
@Controller
@RequestMapping("/")
public class BillController {

    @Autowired
    private BillFeignClient billFeignClient;

    /**
     * 已出账单查询
     * @param request 请求对象
     * @param response 相应对象
     * @param model 传输数据给页面的对象
     * @param pageNo 第几页
     * @param pageSize 有多少行
     * @param CId 卡片的集合List的索引
     * @param selectYearMonth 所选上月的（年、月纯数字）信息
     * @return String bill页面
     * @throws IOException
     */
    @RequestMapping("/bill")
    public String getYiChuBill(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam(value = "pageNo",defaultValue = "1" ) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "1" ) Integer pageSize,
            @RequestParam(value = "CId",defaultValue = "0" ) Integer CId,
            @RequestParam(value = "selectYearMonth", defaultValue = "" )String selectYearMonth
    ) throws IOException {
        /**************************************** 账单汇总 *****************************************/
        /*HttpSession session = request.getSession();
        TbUser user = (TbUser) session.getAttribute("user");
        Long uid = user.getUid();*/
        Long uid = 1L;
        List<TbCreditCardSecurityInfo> ccIdList = billFeignClient.findCardIdListByUid(uid);//【通过uid的查询所有信用片安全信息】
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(ccIdList.isEmpty()){
            out.print("<script>alert('还没有信用卡');location='index.html';</script>");
            out.flush();
            out.close();
            return null; }//没注册卡时跳回主页
        List<TbCreditCardInfo> cardList = new ArrayList<>();//（List对象） -> 用于装【卡号、卡名、id】
        /******************************（1. 计算还款总额；2. 查找用户所有卡片）**********************/
        Long sum = 0L;
        for (int i = 0; i < ccIdList.size(); i++) {
            TbCreditCardSecurityInfo vo = ccIdList.get(i);
            Long ccId = vo.getCcId();//卡号
            TbCreditCardInfo jo = billFeignClient.findCardInfoByCcid(ccId);//【通过卡号查询信用卡信息】
            Long repaidAmount = jo.getRepaidAmount(); //一张卡的需还款金额
            String cardName = jo.getCardName();//卡名
            Long cid = jo.getId();//获取卡片id主键（cid）
            TbCreditCardInfo po = new TbCreditCardInfo();
            po.setCcId(ccId);
            po.setCardName(cardName);
            po.setId(cid);
            cardList.add(po);
            sum+=repaidAmount;//需还款总额
        }
        model.addAttribute("sum",sum);                                  // ==> 需还款总额
        model.addAttribute("cardList",cardList);                       // ==> 卡号、卡名、id
        /************************************* 3. 最近还款日***************************************/
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);//当前年份
        int currentMonth = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_MONTH);//当前日份
        String currentPayDate = getStringZuijinRepayDate(c);//最近还款日
        model.addAttribute("currentPayDate",currentPayDate);           // ==> 最近还款日
        /************************** 4. 遍历出最近已出账单的12个月的年月信息**************************/
        List<String> yearMonthList = new ArrayList<>();
        int month = currentMonth;
        int year = currentYear;
        int day = currentDay;
        if(day>=17){
            String m = getNum(month);
            String yearMonth =  year + m;
            yearMonthList.add(yearMonth);
            for (int k = 0; k < 11; k++ ){
                month -= 1;
                if(month <= 0){
                    month = 12;
                    year -= 1;
                }
                String m_2 = getNum(month);
                String yearMonth_2 =  year + m_2;
                yearMonthList.add(yearMonth_2);
            }
        }if (day<=16){
            for (int j = 0; j < 12; j++ ){
                month -= 1;
                if(month <= 0){
                    month = 12;
                    year -= 1;
                }
                String m = getNum(month);
                String yearMonth =  year + m;
                yearMonthList.add(yearMonth);
            }
        }
        model.addAttribute("yearMonthList",yearMonthList);             // ==> 最近12个月内的已出账单年月

        TbCreditCardInfo cardinfo = cardList.get(CId);//默认第一张卡（0）
        model.addAttribute("CId",CId);                                  // ==> 卡片编号
        Long ccId = cardinfo.getCcId();//卡号
        String yearMonthIndex = yearMonthList.get(0);//默认年月
        model.addAttribute("ymi",yearMonthIndex);                      // ==> 默认年月
        if ("".equals(selectYearMonth)|| yearMonthIndex.equals(selectYearMonth)) {
            model.addAttribute("yearMonthIndex",yearMonthIndex);       // ==> 最近上月账单年月
            /***************************默认账单概要（默认是 最近上月的已结账单概要信息）*****************************/
            String newRepayDate_shang = getRepayDateByYearMonth(yearMonthIndex);//所选月还款日(String)
            Date newrepayDate_shang = StringToDate(newRepayDate_shang);//日期转换 --> (数据库的账单日的时分秒必须是0)
            String shangBillDate = getshangBillday();//最近上个月的账单日(String)
            //Long shangBillDate_Long = Long.valueOf(shangBillDate);//最近上个月的账单日(Long)
            Date shangbilldate = StringToDate(shangBillDate);//日期转换 --> (数据库的账单日的时分秒必须是0)
            TbHistorylMonthbill vo1 = billFeignClient.selectOneMonthBillHistory(shangbilldate, ccId);//【通过最近上月账单日、卡号查询当月历史账单概要信息】
            Long currentRengRepaid = 0L;
            if (vo1 == null){
                System.out.println("vo1 is null");//判断是否获取（月历史账单）对象
            }else {
                Long currentConsumption = vo1.getCurrConsumption();//当月需还款金额
                Long currentRepaid = vo1.getCurrRepaid();//当月已还款金额
                currentRengRepaid = currentConsumption - currentRepaid;//当月仍需还款
            }
            model.addAttribute("currentRengRepaid", currentRengRepaid);     // ==> 当月仍需还款
            model.addAttribute("HistoryMonthBill", vo1);                    // ==> 账单概要(默认的)
            /***************************默认账单明细（默认是 最近上月的已结账单明细信息）*****************************/
            String shangshangBillDate = getshangshangBillday();//最近上上个月的（账单日+1）(String)
            Date shangshangbilldate = StringToDate(shangshangBillDate);//日期转换 --> (数据库的账单日的时分秒必须是0)
            Map<String, Object> map = new HashMap<>();
            map.put("s", shangbilldate);
            map.put("ss", shangshangbilldate);
            map.put("ccid", ccId);
            PageInfo<TbHistoryEverybill> ebpageInfo = billFeignClient.selectOneMonthEveryBillHistory(map, pageNo, pageSize);//【分页显示最近上月账单明细】
            if (ebpageInfo.getPages()==0) System.out.println("ebpageInfo is null");
            model.addAttribute("shangRepayDate",newrepayDate_shang);        // ==> 上个月还款日
            model.addAttribute("shangBillDate", shangbilldate);             // ==> 上个月账单日
            model.addAttribute("shangShangBillDate", shangshangbilldate);   // ==>（上上个月账单日+1）
            model.addAttribute("pageInfo", ebpageInfo);                     // ==> 账单明细
        }else {
            model.addAttribute("yearMonthIndex",selectYearMonth);           // ==> 所选上月账单年月
            /***********************************账单概要（所选月的已结账单概要信息）*********************************/
            String newShangBillDate = selectYearMonth + "16";//所选上月账单日(String)
            Date newshangbilldate = StringToDate(newShangBillDate);//日期转换 --> (数据库的账单日的时分秒必须是0)
            String newRepayDate = getRepayDateByYearMonth(selectYearMonth);//所选月还款日(String)
            Date newrepayDate = StringToDate(newRepayDate);//日期转换 --> (数据库的账单日的时分秒必须是0)
            TbHistorylMonthbill vo2 = billFeignClient.selectOneMonthBillHistory(newshangbilldate, ccId);//【通过所选上月账单日、卡号查询所选月历史账单概要信息】
            if (vo2 == null) System.out.println("vo2 is null");//判断是否获取（月历史账单）对象
            Long currentRengRepaid = 0L;
            model.addAttribute("currentRengRepaid", currentRengRepaid);    // ==> 当月仍需还款
            model.addAttribute("HistoryMonthBill", vo2);                   // ==> 账单概要(所选上月的)
            /***********************************账单明细（所选月的已结账单明细信息）*********************************/
            //所选上月的上个月的（账单日+1）
            String newShangShangBillDate = getStringShangBillDateByYearMonth(selectYearMonth);//所选上月的上个月的（账单日+1）(String)
            Date newshangshangbilldate = StringToDate(newShangShangBillDate);//日期转换 --> (数据库的账单日的时分秒必须是0)
            Map<String, Object> map_1 = new HashMap<>();
            map_1.put("s", newshangbilldate);
            map_1.put("ss", newshangshangbilldate);
            map_1.put("ccid", ccId);
            PageInfo<TbHistoryEverybill> ebpageInfo = billFeignClient.selectOneMonthEveryBillHistory(map_1, pageNo, pageSize);//【分页显示所选上月账单明细】
            System.out.println(ebpageInfo);
            if (ebpageInfo.getPages()==0) System.out.println("ebpageInfo is null");
            model.addAttribute("repayDate",newrepayDate);                      // ==> 所选月还款日
            model.addAttribute("shangBillDate", newshangbilldate);             // ==> 所选上月账单日
            model.addAttribute("shangShangBillDate", newshangshangbilldate);   // ==>（所选上月的上个月账单日+1）
            model.addAttribute("pageInfo", ebpageInfo);                        // ==> 账单明细
        }
        return "bill";
    }

    private String getStringZuijinRepayDate(Calendar c) {
        int cYear = c.get(Calendar.YEAR);//当前年份
        int cMonth = (c.get(Calendar.MONTH)+1);//当前月份
        int cDay = c.get(Calendar.DAY_OF_MONTH);//当前日份
        if (cDay > 4){
            cMonth += 1;
            if(cMonth >= 13){
                cMonth = 12;
                cYear += 1;
            }
        }
        String dangmonth = getNum(cMonth);//（处理数字，格式：两位数）
        return cYear +"年 "+ dangmonth +"月 04日";
    }

    /**
     * 根据所选上月的年月 找寻 所选上月的上月的（账单日+1）
     * @param selectYearMonth 所选上月的年月
     * @return String
     */
    private String getStringShangBillDateByYearMonth(@RequestParam(value = "selectYearMonth", defaultValue = "") String selectYearMonth) {
        String shiWei = selectYearMonth.substring(4, 5);//十位
        String geWei = selectYearMonth.substring(5, 6);//各位
        String fullWei = selectYearMonth.substring(4);//所有位
        String yearWei = selectYearMonth.substring(0,4);//年位
        int year_I = Integer.valueOf(yearWei);
        int month_I = 0;
        if ("0".equals(shiWei)){
            month_I = Integer.valueOf(geWei);
        }else if (!"0".equals(shiWei)){
            month_I = Integer.valueOf(fullWei);
        }
        month_I -= 1;
        if(month_I <= 0){
            month_I = 12;
            year_I -= 1;
        }
        String shangMonth_1 = getNum(month_I);
        return year_I + "" + shangMonth_1 + "17";
    }

    /**
     * 根据所选上月的年月 找寻 所选上月的下月的还款日
     * @param selectYearMonth 所选上月的年月
     * @return String
     */
    private String getRepayDateByYearMonth(@RequestParam(value = "selectYearMonth", defaultValue = "") String selectYearMonth) {
        String shiWei = selectYearMonth.substring(4, 5);//十位
        String geWei = selectYearMonth.substring(5, 6);//各位
        String fullWei = selectYearMonth.substring(4);//所有位
        String yearWei = selectYearMonth.substring(0,4);//年位
        int year_I = Integer.valueOf(yearWei);
        int month_I = 0;
        if ("0".equals(shiWei)){
            month_I = Integer.valueOf(geWei);
        }else if (!"0".equals(shiWei)){
            month_I = Integer.valueOf(fullWei);
        }
        month_I += 1;
        if(month_I >= 13){
            month_I = 1;
            year_I += 1;
        }
        String xiaMonth_1 = getNum(month_I);
        return year_I + "" + xiaMonth_1 + "04";
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
     * 查找最近上月的账单日
     * @return String
     */
    private String getshangBillday() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);//当前年份
        int month = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_MONTH);//当前日份
        if (currentDay < 16){
            month -= 1;
            if(month <= 0){
                month = 12;
                year -= 1;
            }
        }
        String shangmonth = getNum(month);
        return year +""+ shangmonth +"16";
    }

    /**
     * 查找最近上上月的（账单日+1）
     * @return String
     */
    private String getshangshangBillday() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);//当前年份
        int month = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_MONTH);//当前日份
        if (currentDay < 16){
            for (int p = 0; p < 2; p++) {
                month -= 1;
                if (month <= 0) {
                    month = 12;
                    year -= 1;
                }
            }
        }
        String shangshangmonth = getNum(month);
        return year +""+ shangshangmonth +"17";
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
