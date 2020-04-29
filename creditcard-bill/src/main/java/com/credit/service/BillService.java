package com.credit.service;

import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistorylMonthbill;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 已出账单
 */
public interface BillService {

    /**
     * 通过（上个月账单日）、（卡号） 查找1个 （上个月的历史账单概要）
     * @param OneMonthbillday 上个月账单日
     * @param ccid 卡号
     * @return
     */
    public TbHistorylMonthbill selectOneMonthbillhistory(Date OneMonthbillday, Long ccid);

    /**
     * 通过（上个月账单日）、（上上个月账单日-1）、（卡号）分页查找n个（上个月的每笔历史账单明细）
     * @param s 上个月账单日
     * @param ss （上上个月账单日-1）
     * @param ccid 所选的卡号
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    public PageInfo<TbHistoryEverybill> selectOneMontheverybillhistory(Date s, Date ss, Long ccid, Integer pageNo, Integer pageSize);


    /**
     * 多条件查询相应时间内的账单明细
     *  s 上个月账单日
     *  ss （上上个月账单日-1）
     *  ccid 所选的卡号
     * @param  map
     * @return
     */
    public List<TbHistoryEverybill> getOneMonthEverybillHistory(Map<String,Object> map);


}
