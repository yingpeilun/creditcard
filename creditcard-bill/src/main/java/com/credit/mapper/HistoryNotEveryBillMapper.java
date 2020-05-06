package com.credit.mapper;

import com.credit.com.credit.common.BaseMapper;
import com.credit.pojo.TbHistoryNotEverybill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HistoryNotEveryBillMapper extends BaseMapper<TbHistoryNotEverybill> {
    /**
     * 多条件查询相应时间内的某张卡的账单明细
     *  s (上个月账单日+1)
     *  p （当月还款日-1）
     *  ccid 所选的卡号
     * @param  map
     * @return
     */
    public List<TbHistoryNotEverybill> getOneMonthEveryNotbillHistory(Map<String,Object> map);
}
