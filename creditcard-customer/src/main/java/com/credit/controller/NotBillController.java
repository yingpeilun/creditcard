package com.credit.controller;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.service.BillFeignClient;
import com.github.pagehelper.PageInfo;
import com.sun.media.sound.MidiOutDeviceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class NotBillController {

    @Autowired
    private BillFeignClient billFeignClient;

    @RequestMapping("/notbill")
    public String getWeiChuBill(HttpServletRequest request, HttpServletResponse response, Model model,
           @RequestParam(value = "pageNo",defaultValue = "1" ) Integer pageNo,
           @RequestParam(value = "pageSize",defaultValue = "1" ) Integer pageSize,
           @RequestParam(value = "CId",defaultValue = "0" ) Integer CId
    ) throws IOException
    {
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
        /******************************（1. 查找用户所有卡片）**********************/
        for (int i = 0; i < ccIdList.size(); i++) {
            TbCreditCardSecurityInfo vo = ccIdList.get(i);
            Long ccId = vo.getCcId();//卡号
            TbCreditCardInfo jo = billFeignClient.findCardInfoByCcid(ccId);//【通过卡号查询信用卡信息】
            String cardName = jo.getCardName();//卡名
            TbCreditCardInfo po = new TbCreditCardInfo();
            po.setCcId(ccId);
            po.setCardName(cardName);
            cardList.add(po);
        }
        model.addAttribute("cardList",cardList);                            //==> 卡号和卡名
        TbCreditCardInfo cardinfo = cardList.get(CId);//默认第一张卡
        Long ccId = cardinfo.getCcId();//卡号
        String currentPayDate_jian1 = getStringCurrentPayDate();//（最近还款日-1）(String)
        Date currentpaydate = StringToDate(currentPayDate_jian1);//日期转换 --> (数据库的账单日的时分秒必须是0)
        model.addAttribute("currentPayDate",currentpaydate);                // ==> 最近还款日

        String shangBillDate_jia1 = getshangBillday();//最近上月（账单日+1）(String)
        Date shangbilldate = StringToDate(shangBillDate_jia1);//日期转换 --> (数据库的账单日的时分秒必须是0)
        model.addAttribute("shangBillDate", shangbilldate);                 // ==> 最近上月的（账单日+1）
        /***********************************账单明细（当月的未出账单明细信息）*********************************/
        Map<String, Object> map = new HashMap<>();
        map.put("s", shangbilldate);
        map.put("p", currentpaydate);
        map.put("ccid", ccId);
        PageInfo<TbHistoryNotEverybill> enbpageInfo = billFeignClient.selectOneMonthEveryNotBillHistory(map, pageNo, pageSize);//【分页显示当月未出账单明细】
        System.out.println(enbpageInfo);
        if (enbpageInfo == null) System.out.println("enbpageInfo is null");
        model.addAttribute("pageInfo", enbpageInfo);                        // ==> 账单明细
        return "notbill";
    }

    /**
     * 查找最近上月的（账单日+1）
     * @return String
     */
    private String getshangBillday() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);//当前年份
        int month = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_WEEK);//当前日份
        if (currentDay < 16){
            month -= 1;
            if(month <= 0){
                month = 12;
                year -= 1;
            }
        }
        String shangmonth = getNum(month);
        return year +""+ shangmonth +"17";
    }
    /**
     * 查找（最近还款日-1）
     * @return String
     */
    private String getStringCurrentPayDate() {
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);//当前年份
        int currentMonth = (c.get(Calendar.MONTH)+1);//当前月份
        int currentDay = c.get(Calendar.DAY_OF_WEEK);//当前日份
        if (currentDay > 4){
            currentMonth += 1;
            if(currentMonth <= 0){
                currentMonth = 12;
                currentYear += 1;
            }
        }
        String dangmonth = getNum(currentMonth);//（处理数字，格式：两位数）
        return currentYear +""+ dangmonth +"03";
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
