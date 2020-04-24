package com.credit.mapper;

import com.credit.pojo.TbCreditCardSecurityInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CreditCardSecurityInfoMapper extends Mapper<TbCreditCardSecurityInfo> {
}
