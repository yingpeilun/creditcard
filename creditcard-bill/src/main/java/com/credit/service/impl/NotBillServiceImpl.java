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
     * 通过（上个月账单日）、（上上个月账单日-1）、（卡号）分页查找n个（上个月的每笔历史账单明细）
     * @param s (上个月账单日+1)
     * @param p （当月还款日-1）
     * @param ccid 所选的卡号
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    public PageInfo<TbHistoryNotEverybill> selectOneMontheveryNotbillhistory(Date s, Date p, Long ccid, Integer pageNo, Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("s",s);
        map.put("p",p);
        map.put("ccid",ccid);
        List<TbHistoryNotEverybill> list = this.getOneMonthEveryNotbillHistory(map);
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
}
