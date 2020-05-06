package com.credit.controller;

import com.credit.service.BillFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notbill")
public class NotBillController {

    @Autowired
    private BillFeignClient billFeignClient;

    @RequestMapping("/bill")
    public String getYiChuBill(){
        return "";
    }
}
