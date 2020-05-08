package com.credit.service.impl;

import com.credit.mapper.*;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistorylMonthbill;
import com.credit.service.BillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {

    @Resource
    private HistoryEveryBillMapper historyEveryBillMapper;

    @Resource
    private HistorylMonthBillMapper historylMonthBillMapper;

    /**
     * 查询某卡 的（月已出账单）List集合
     * @param vo TbHistorylMonthbill对象
     * @return List<TbHistorylMonthbill>
     */
    public List<TbHistorylMonthbill> getCardMonthBillList(TbHistorylMonthbill vo){
        return historylMonthBillMapper.select(vo);
    }
    /**
     * 通过（已出月账单日）、（卡号） 查找1个 （月历史账单概要）
     * @param oneMonthbillday 上个月账单日
     * @param ccid 卡号
     * @return
     */
    public TbHistorylMonthbill selectOneMonthbillhistory(Date oneMonthbillday, Long ccid){
        TbHistorylMonthbill vo = new TbHistorylMonthbill();
        vo.setBillDate(oneMonthbillday);
        vo.setCcId(ccid);
        return historylMonthBillMapper.selectOne(vo);
    }

    /**
     * 通过（已出月账单日）、（卡号） 查找1个 （月历史账单概要）
     * @param oneMonthbillday 上个月账单日
     * @param ccid 卡号
     * @return
     */
    public TbHistorylMonthbill selectOneMonthbillhistory(Long oneMonthbillday, Long ccid){
        TbHistorylMonthbill vo = new TbHistorylMonthbill();
        vo.setBillDateNum(oneMonthbillday);
        vo.setCcId(ccid);
        return historylMonthBillMapper.selectOne(vo);
    }

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
    public PageInfo<TbHistoryEverybill> selectOneMontheverybillhistory(Map<String,Object> map, Integer pageNo, Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TbHistoryEverybill> list = historyEveryBillMapper.getOneMonthEverybillHistory(map);
        return new PageInfo<>(list);
    }

    /**
     * 多条件查询相应时间内的某张卡的已出账单明细
     *  s 上个月账单日
     *  ss （上上个月账单日-1）
     *  ccid 所选的卡号
     * @param map
     * @return
     */
    public List<TbHistoryEverybill> getOneMonthEverybillHistory(Map<String,Object> map){
        return historyEveryBillMapper.getOneMonthEverybillHistory(map);
    }

    /**
     * 添加 一笔封装好的已出账单 到 已出账单表(每笔的)
     * @param vo 一笔封装好的已出账单
     * @return boolean
     */
    public boolean inputOneBill(TbHistoryEverybill vo){
        int ob = historyEveryBillMapper.insert(vo);
        return ob > 0 ? true : false;
    }

    /**
     * 添加 每一笔封装好的已出账单(List) 到 已出账单表(每笔的)
     * @param voList List<TbHistoryEverybill>
     * @return boolean
     */
    public boolean inputListBill(List<TbHistoryEverybill> voList){
        int i = historyEveryBillMapper.insertList(voList);
        return i > 0 ? true : false;
    }

    /**
     * 添加已结账的月账单进入月已出账单表
     * @param vo TbHistorylMonthbill
     * @return boolean
     */
    public boolean inputMonthBill(TbHistorylMonthbill vo){
        int i = historylMonthBillMapper.insertSelective(vo);
        return i > 0 ? true : false ;
    }
}
