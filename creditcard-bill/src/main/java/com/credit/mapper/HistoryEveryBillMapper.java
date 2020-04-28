package com.credit.mapper;

import com.credit.com.credit.common.BaseMapper;
import com.credit.pojo.TbHistoryEverybill;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
public interface HistoryEveryBillMapper extends BaseMapper<TbHistoryEverybill> {
}
