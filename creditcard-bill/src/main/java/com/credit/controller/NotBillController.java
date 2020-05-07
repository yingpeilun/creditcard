package com.credit.controller;

import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.service.BaseService;
import com.credit.service.BillService;
import com.credit.service.ClientService;
import com.credit.service.NotBillService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 账户管理：未出账单
 */
@Controller
@RequestMapping("/")
public class NotBillController {

    @Autowired
    private NotBillService notBillService;

    @Autowired
    private ClientService clientService;


    /**
     * 通过（上个月账单日+1）、（当月还款日-1）、（卡号）分页查找n个（当月的每笔历史账单明细）
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param map s p ccid
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    @PostMapping("/notbill/selectOneMonthEveryNotBillHistory")
    @ResponseBody
    public PageInfo<TbHistoryNotEverybill> selectOneMonthEveryNotBillHistory(@RequestBody Map<String,Object> map,@RequestParam("pageNo") Integer pageNo,@RequestParam("pageSize") Integer pageSize){
        return notBillService.selectOneMontheveryNotbillhistory(map, pageNo, pageSize);
    }

    /**
     * 通过（上个月账单日+1）、（当月还款日-1）、（卡号）查找n个（当月的每笔历史账单明细）
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param map s p ccid
     * @return List<TbHistoryNotEverybill>
     */
    @PostMapping("/notbill/getOneMonthEveryNotBillHistory")
    @ResponseBody
    public List<TbHistoryNotEverybill> getOneMonthEveryNotBillHistory(@RequestBody Map<String,Object> map){
        return notBillService.getOneMonthEveryNotbillHistory(map);
    }
}
