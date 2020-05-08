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
     * 查询某卡 的（月已出账单）List集合
     * @param vo TbHistorylMonthbill对象
     * @return
     */
    public List<TbHistorylMonthbill> getCardMonthBillList(TbHistorylMonthbill vo);
    /**
     * 通过（最近上个月账单日）、（卡号） 查找1个 （上个月的历史账单概要）
     * @param OneMonthbillday 最近上个月账单日
     * @param ccid 卡号
     * @return
     */
    public TbHistorylMonthbill selectOneMonthbillhistory(Date OneMonthbillday, Long ccid);

    /**
     * 通过（最近上个月账单日）、（卡号） 查找1个 （上个月的历史账单概要）
     * @param OneMonthbillday 最近上个月账单日
     * @param ccid 卡号
     * @return
     */
    public TbHistorylMonthbill selectOneMonthbillhistory(Long OneMonthbillday, Long ccid);

    /**
     * 通过（最近上月账单日）、（最近上上月账单日-1）、（卡号）分页查找n个（最近上月的每笔历史账单明细）
     * s （上上个月账单日+1）
     * ss 上个月账单日
     * ccid 所选的卡号
     * @param map s ss ccid
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    public PageInfo<TbHistoryEverybill> selectOneMontheverybillhistory(Map<String,Object> map, Integer pageNo, Integer pageSize);


    /**
     * 多条件查询相应时间内的某张卡的账单明细
     *  s 上个月账单日
     *  ss （上上个月账单日-1）
     *  ccid 所选的卡号
     * @param  map
     * @return
     */
    public List<TbHistoryEverybill> getOneMonthEverybillHistory(Map<String,Object> map);

    /**
     * 添加 一笔封装好的已出账单 到 已出账单表(每笔的)
     * @param vo 一笔封装好的已出账单
     * @return boolean
     */
    public boolean inputOneBill(TbHistoryEverybill vo);

    /**
     * 添加 每一笔封装好的已出账单(List) 到 已出账单表(每笔的)
     * @param voList List<TbHistoryEverybill>
     * @return boolean
     */
    public boolean inputListBill(List<TbHistoryEverybill> voList);

    /**
     * 添加已结账的月账单进入月已出账单表
     * @param vo TbHistorylMonthbill
     * @return boolean
     */
    public boolean inputMonthBill(TbHistorylMonthbill vo);

}
