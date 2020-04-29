package com.credit.controller;

import com.credit.pojo.*;
import com.credit.service.BaseService;
import com.credit.service.BillService;
import com.credit.service.NotBillService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    /**
     * 前端账单查询
     * @param pageNo 第几页
     * @param pageSize 显示几行
     * @param request 请求封装对象
     * @param model 响应model传值对象
     * @param CId 信用卡主键id
     * @return
     */
    @RequestMapping(value = "/bill")
    public String getyichubill(
            @RequestParam(value = "pageNo",defaultValue = "1" ) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "8" ) Integer pageSize,
            HttpServletRequest request, Model model,
            @RequestParam("CId") Long CId){
        //*************账单汇总(需还款总额,显示卡号和卡名)**********
        HttpSession session = request.getSession();
        TbUser user = (TbUser) session.getAttribute("user");
        Long uid = user.getUid();
        //通过uid的查询所有卡片信息
        List<TbCreditCardSecurityInfo> cardIdList = baseService.findCardidlistbyUid(uid);
        if(cardIdList.isEmpty()){//没注册卡时跳回主页
            return "主页面";
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
        String dangmonth = getNum(currentMonth);
        //int day = c.get(Calendar.DAY_OF_WEEK);//日
                //最近还款日
        String currentPayDate = currentYear +""+ dangmonth +"04";
        model.addAttribute("currentPayDate",currentPayDate);           //==> 最近还款日

        List<String> yearMonthlsit = new ArrayList<String>();
        //（调用获取12个月的已出账单）
        baseService.getYearMonth12(currentMonth, currentYear, yearMonthlsit);
        model.addAttribute("yearMonthlsit",yearMonthlsit);  //==> 12个月内的已出账单年月

        //***********账单概要（默认是 上个月的账单信息）***********
        // 点击账单查询默认：第一个卡号【cardlsit里的get（0）】；否则是所传卡号的
        // 通过（上个月账单日）、（卡号） 查找1个 （上个月的历史账单概要）

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 有CId时
        for (int o = 0; o <cardlsit.size();o++ ){
            TbCreditCardInfo cardinfo = cardlsit.get(o);
            Long cid = cardinfo.getId();
            if(CId == cid){
                model.addAttribute("CId",CId);
                //卡号
                Long ccId = cardinfo.getCcId();
                //获取当前上个月的账单日
                String shangBillDate = baseService.getBillday(currentYear, currentMonth);
                //日期转换
                Date shangbilldate = baseService.getDate(sdf, shangBillDate);
                //查询上月账单概要
                TbHistorylMonthbill vo = billService.selectOneMonthbillhistory(shangbilldate, ccId);
                model.addAttribute("HistorylMonthbill",vo);     //==> 账单概要（通过CId确认哪张卡片）
                //上上个月的（账单日+1）
                String shangshangBillDate = baseService.getshangshangBillDate(currentYear, currentMonth);
                //日期转换
                Date shangshangbilldate1 = baseService.getDate(sdf, shangshangBillDate);
                //多条件分页查询上月的账单明细
                PageInfo<TbHistoryEverybill> EbillPageInfo = billService.selectOneMontheverybillhistory(shangbilldate, shangshangbilldate1, ccId, pageNo, pageSize);
                model.addAttribute("ebpageinfo",EbillPageInfo);
            }
        }
        //无CId时
        TbCreditCardInfo cardinfo1 = cardlsit.get(0);//默认第一张卡
        Long ccId1 = cardinfo1.getCcId();//卡号
        String shangBillDate1 = baseService.getBillday(currentYear, currentMonth);//上个月的账单日
        //日期转换
        Date shangbilldate1 = baseService.getDate(sdf, shangBillDate1);
        //查询上月账单概要
        TbHistorylMonthbill vo1 = billService.selectOneMonthbillhistory(shangbilldate1, ccId1);
        model.addAttribute("HistorylMonthbill",vo1);     //==> 账单概要(默认的)

        //***********账单明细（默认是 上个月的账单信息）***********
        //1 查找 （上个月账单日） 和 （上上个月账单日+1）
        //2 通过（上个月账单日与上上个月账单日之间的时间）、（卡号）分页查找n个（上个月的每笔历史账单明细）

        //上上个月的（账单日+1）
        String shangshangBillDate = baseService.getshangshangBillDate(currentYear, currentMonth);
        //日期转换
        Date shangshangbilldate1 = baseService.getDate(sdf, shangshangBillDate);
        //多条件分页查询上月的账单明细
        PageInfo<TbHistoryEverybill> EbillPageInfo = billService.selectOneMontheverybillhistory(shangbilldate1, shangshangbilldate1, ccId1, pageNo, pageSize);
        model.addAttribute("ebpageinfo",EbillPageInfo);
        return "已出账单查询页面";
    }

    /**
     * 处理日期int前面的无零的问题
     * @param num 原数
     * @return
     */
    private static String getNum(int num){
        return num > 9 ? "" + num : "0" + num;
    }


}
