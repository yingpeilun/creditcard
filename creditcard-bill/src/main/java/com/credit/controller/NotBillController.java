package com.credit.controller;

import com.credit.service.NotBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 未出账单
 */
@Controller
@RequestMapping("/notbill")
public class NotBillController {

    @Autowired
    private NotBillService notBillService;

}
