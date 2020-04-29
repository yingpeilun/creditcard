package com.credit.controller;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.pojo.TbUser;
import com.credit.service.BaseService;
import com.credit.service.BillService;
import com.credit.service.NotBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * 已出账单
 */
@Controller
@RequestMapping("/")
public class BillController {

    @Autowired
    private BaseService baseService;

    @Autowired
    private BillService billService;

    @Autowired
    private NotBillService notBillService;

    @RequestMapping(value = "/bill")
    public String getyichubill(
            HttpServletRequest request,
            Model model){
        //*************账单汇总(需还款总额,显示卡号和卡名)**********
        HttpSession session = request.getSession();
        TbUser user = (TbUser) session.getAttribute("user");
        Long uid = user.getUid();
        //通过uid的查询所有卡片信息
        List<TbCreditCardSecurityInfo> cardIdList = baseService.findCardidlistbyUid(uid);
        if(cardIdList == null){
            return "zhuye";
        }
        //对象里用于装（卡号和卡名）
        List<TbCreditCardInfo> cardlsit = new ArrayList<TbCreditCardInfo>();
        //（计算还款总额和查找用户卡片）
        Long sum = baseService.getaLong(cardIdList, cardlsit);
        model.addAttribute("sum",sum);              //==> 需还款总额
        model.addAttribute("cardlsit",cardlsit);    //==> 卡号和卡名

        //***************账单汇总(最近还款日)********************
        Calendar c = Calendar.getInstance();
                //当前年份
        int currentYear = c.get(Calendar.YEAR);
                //当前月份
        int currentMonth = (c.get(Calendar.MONTH)+1);
        //（处理数字，格式：两位数）
        String month = getNum(currentMonth);
        //int day = c.get(Calendar.DAY_OF_WEEK);
                //最近还款日
        String date = currentYear +""+ month +"04";
        model.addAttribute("date",date);           //==> 最近还款日

        List<String> yearMonthlsit = new ArrayList<String>();
        //（调用获取12个月的已出账单）
        baseService.getYearMonth12(currentMonth, currentYear, yearMonthlsit);
        model.addAttribute("yearMonthlsit",yearMonthlsit);  //==> 12个月内的已出账单年月

        //***********账单概要（默认是 上个月的账单信息）***********
        // 第一个卡号【cardlsit里的get（1）】
        // 通过（上个月账单日）、（卡号） 查找1个 （上个月的历史账单概要）



        //***********账单明细（默认是 上个月的账单信息）***********
        //1 查找 （上个月账单日） 和 （上上个月账单日）
        //2 通过（上个月账单日与上上个月账单日之间的时间）、（卡号）分页查找n个（上个月的每笔历史账单明细）





        return "";
    }


    /**
     * 处理日期int前面的无零的问题
     * @param num 原数
     * @return
     */
    public static String getNum(int num){
        return num > 9 ? "" + num : "0" + num;
    }


}
