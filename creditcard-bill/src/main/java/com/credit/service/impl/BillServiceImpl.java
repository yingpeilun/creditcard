package com.credit.service.impl;

import com.credit.mapper.*;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbHistoryEverybill;
import com.credit.pojo.TbHistorylMonthbill;
import com.credit.service.BillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
     * 通过（上个月账单日）、（卡号） 查找1个 （上个月的历史账单概要）
     * @param OneMonthbillday 上个月账单日
     * @param ccid 卡号
     * @return
     */
    public TbHistorylMonthbill selectOneMonthbillhistory(Date OneMonthbillday, Long ccid){
        TbHistorylMonthbill vo = new TbHistorylMonthbill();
        vo.setBillDate(OneMonthbillday);
        vo.setCcId(ccid);
        return historylMonthBillMapper.selectOne(vo);
    }

    /**
     * 通过（上个月账单日）、（上上个月账单日-1）、（卡号）分页查找n个（上个月的每笔历史账单明细）
     * @param s 上个月账单日
     * @param ss （上上个月账单日-1）
     * @param ccid 所选的卡号
     * @param pageNo 第几页
     * @param pageSize 要几行
     * @return PageInfo pagehelper对象的PageInfo，方便分页
     */
    public PageInfo<TbHistoryEverybill> selectOneMontheverybillhistory(Date s, Date ss, Long ccid, Integer pageNo, Integer pageSize){
        PageHelper.startPage(pageNo,pageSize);
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("s",s);
        map.put("ss",ss);
        map.put("ccid",ccid);
        List<TbHistoryEverybill> list = this.getOneMonthEverybillHistory(map);
        return new PageInfo<>(list);
    }

    /**
     * 多条件查询相应时间内的某张卡的账单明细
     *  s 上个月账单日
     *  ss （上上个月账单日-1）
     *  ccid 所选的卡号
     * @param map
     * @return
     */
    public List<TbHistoryEverybill> getOneMonthEverybillHistory(Map<String,Object> map){
        return historyEveryBillMapper.getOneMonthEverybillHistory(map);
    }
}
