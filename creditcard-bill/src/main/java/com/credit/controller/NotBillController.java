package com.credit.controller;

import com.credit.service.BaseService;
import com.credit.service.NotBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账户管理：未出账单
 */
@Controller
@RequestMapping("/")
public class NotBillController {
    @Autowired
    private NotBillService notBillService;

    @Autowired
    private BaseService baseService;
}
