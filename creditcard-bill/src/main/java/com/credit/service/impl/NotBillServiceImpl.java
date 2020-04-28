package com.credit.service.impl;

import com.credit.mapper.HistoryNotEveryBillMapper;
import com.credit.mapper.HistorylNotMonthBillMapper;
import com.credit.service.NotBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class NotBillServiceImpl implements NotBillService {
    @Resource
    private HistoryNotEveryBillMapper historyNotEveryBillMapper;

    @Resource
    private HistorylNotMonthBillMapper historylNotMonthBillMapper;
}
