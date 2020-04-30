package com.credit.controller;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.pojo.TbUser;
import com.credit.service.BaseService;
import com.credit.service.NotBillService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 未出账单
 */
@Controller
@RequestMapping("/")
public class NotBillController {

    @Autowired
    private NotBillService notBillService;

    @Autowired
    private BaseService baseService;

    @RequestMapping(value = "/notbill")
    public String getyichubill(
            @RequestParam(value = "pageNo",defaultValue = "1" ) Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "8" ) Integer pageSize,
            HttpServletRequest request,
            Model model,
            @RequestParam("CId") Long CId){
        //*************（显示卡号和卡名)**********
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
        if(!cardIdList.isEmpty()) {
            for (int i = 0; i < cardIdList.size(); i++) {
                TbCreditCardSecurityInfo vo = cardIdList.get(i);
                Long ccId = vo.getCcId();//卡号
                TbCreditCardInfo jo = baseService.findCardInfobyCcid(ccId);
                String cardName = jo.getCardName();//卡名
                Long cid = jo.getId();//获取卡片id主键（cid）
                TbCreditCardInfo po = new TbCreditCardInfo();
                po.setCcId(ccId);
                po.setCardName(cardName);
                po.setId(cid);
                cardlsit.add(po);
            }
        }
        model.addAttribute("cardlsit",cardlsit);    //==> 卡号和卡名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        TbCreditCardInfo cardinfo = cardlsit.get(0);//默认第一张卡
        Long ccId = cardinfo.getCcId();//卡号
        //获取当前时间
        Calendar c = Calendar.getInstance();
        //当前年份
        int currentYear = c.get(Calendar.YEAR);
        //当前月份
        int currentMonth = (c.get(Calendar.MONTH)+1);
        //（处理数字，格式：两位数）
        String dangmonth = getNum(currentMonth);
        //(最近还款日-1)
        String currentPayDate = currentYear +""+ dangmonth +"03";
        //(上月账单日+1)
        String currentBillDate = baseService.getshangBillDate_1(currentYear, currentMonth);

        Date date1 = baseService.getDate(sdf, currentBillDate);
        Date date2 = baseService.getDate(sdf, currentPayDate);

        PageInfo<TbHistoryNotEverybill> NotEbPageInfo = notBillService.selectOneMontheveryNotbillhistory(date1, date2, ccId, pageNo, pageSize);
        model.addAttribute("notebpageinfo",NotEbPageInfo);
        return "未出账单查询页面";
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
