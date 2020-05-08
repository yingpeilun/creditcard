package com.credit.service.impl;

import com.credit.mapper.HistoryNotEveryBillMapper;
import com.credit.mapper.HistorylNotMonthBillMapper;
import com.credit.pojo.TbHistoryNotEverybill;
import com.credit.service.NotBillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotBillServiceImpl implements NotBillService {
    @Resource
    private HistoryNotEveryBillMapper historyNotEveryBillMapper;

    @Resource
    private HistorylNotMonthBillMapper historylNotMonthBillMapper;

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
    public PageInfo<TbHistoryNotEverybill> selectOneMontheveryNotbillhistory(Map<String,Object> map, Integer pageNo, Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TbHistoryNotEverybill> list = historyNotEveryBillMapper.getOneMonthEveryNotbillHistory(map);
        return new PageInfo<>(list);
    }


    /**
     * 多条件查询相应时间内的某张卡的未出账单明细
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param  map
     * @return
     */
    public List<TbHistoryNotEverybill> getOneMonthEveryNotbillHistory(Map<String,Object> map){
        return historyNotEveryBillMapper.getOneMonthEveryNotbillHistory(map);
    }

    /**
     * 通过TbHistoryNotEverybill对象插入一笔交易未出账单
     * @param vo TbHistoryNotEverybill对象
     * @return
     */
    public boolean insertOneEveryBill(TbHistoryNotEverybill vo){
        int insert = historyNotEveryBillMapper.insert(vo);
        return insert>0?true:false;
    }
}
