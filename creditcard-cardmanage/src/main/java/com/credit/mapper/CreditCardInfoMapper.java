package com.credit.mapper;

import com.credit.pojo.TbCreditCardInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CreditCardInfoMapper extends Mapper<TbCreditCardInfo> {
}
