package com.credit.controller;

import com.credit.api.BillApi;
import com.credit.cilent.BillClient;
import com.credit.cilent.RepaymentClient;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class RepaymentController {

    @Resource
    private RepaymentClient repaymentClient;

    @Resource
    private BillClient billClient;


    @GetMapping("finduid")
    public String finduid(@RequestParam("uid") Long uid, Model model){
        List<TbCreditCardSecurityInfo> list=billClient.findCardIdListByUid(uid);
        model.addAttribute("list",list);
        return "repayment";
    }


    @PostMapping("indrepaidamount")
    public String findrepaidamount(String ccId,Model model){
        Long value=Long.valueOf(ccId);
        TbCreditCardInfo bCreditCardInfo=billClient.findCardInfoByCcid(value);
        model.addAttribute("bCreditCardInfo",bCreditCardInfo);
        return "repayment";
    }
}
