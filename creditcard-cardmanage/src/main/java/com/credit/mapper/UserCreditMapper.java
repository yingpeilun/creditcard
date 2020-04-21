package com.credit.mapper;

import com.credit.pojo.TbUserCredit;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserCreditMapper extends Mapper<TbUserCredit> {
}
