package com.credit.controller;

import com.credit.service.BaseService;
import com.credit.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账户管理：已出账单
 */
@Controller
@RequestMapping("/")
public class BillController {
    @Autowired
    private BaseService baseService;

    @Autowired
    private BillService billService;



    /**
     * 处理日期int前面的无零的问题
     * @param num 原数
     * @return
     */
    private static String getNum(int num){
        return num > 9 ? "" + num : "0" + num;
    }


}
