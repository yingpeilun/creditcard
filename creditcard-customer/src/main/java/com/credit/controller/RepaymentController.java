package com.credit.controller;

import com.credit.api.BillApi;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.service.BillFeignClient;
import com.credit.service.RepaymentFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RepaymentController {

    @Autowired
    private RepaymentFeignClient repaymentFeignClient;

    @Autowired
    private BillFeignClient billFeignClient;


    @GetMapping("finduid")
    public String finduid(@RequestParam("uid") Long uid, Model model){
        List<TbCreditCardSecurityInfo> list=billFeignClient.findCardIdListByUid(uid);
        model.addAttribute("list",list);
        return "repayment";
    }


    @PostMapping("indrepaidamount")
    public String findrepaidamount(String ccId,Model model){
        Long value=Long.valueOf(ccId);
        TbCreditCardInfo bCreditCardInfo=billFeignClient.findCardInfoByCcid(value);
        model.addAttribute("bCreditCardInfo",bCreditCardInfo);
        return "repayment";
    }
}
