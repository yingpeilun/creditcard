package com.credit.mapper;

import com.credit.com.credit.common.BaseMapper;
import com.credit.pojo.TbHistoryEverybill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
public interface HistoryEveryBillMapper extends BaseMapper<TbHistoryEverybill> {

    /**
     * 多条件查询相应时间内的账单明细
     * @param s 上个月账单日
     * @param ss （上上个月账单日-1）
     * @param ccid 所选的卡号
     * @return
     */
    public List<TbHistoryEverybill> getOneMonthEverybillHistory(Date s, Date ss, Long ccid);
}
