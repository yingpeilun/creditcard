package com.credit.service;

import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistoryNotEverybill;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 未出账单
 */
public interface NotBillService {

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
    public PageInfo<TbHistoryNotEverybill> selectOneMontheveryNotbillhistory(Map<String,Object> map, Integer pageNo, Integer pageSize);


    /**
     * 多条件查询相应时间内的某张卡的账单明细
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param  map
     * @return
     */
    public List<TbHistoryNotEverybill> getOneMonthEveryNotbillHistory(Map<String,Object> map);

    /**
     * 通过TbHistoryNotEverybill对象插入一笔交易未出账单
     * @param vo TbHistoryNotEverybill对象
     * @return
     */
    public boolean insertOneEveryBill(TbHistoryNotEverybill vo);

}
