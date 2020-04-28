package com.credit.service.impl;

import com.credit.mapper.*;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.service.BillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BillServiceImpl implements BillService {

    @Resource
    private HistoryEveryBillMapper historyEveryBillMapper;

    @Resource
    private HistorylMonthBillMapper historylMonthBillMapper;


}
