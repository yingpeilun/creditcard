package com.credit.mapper;

import com.credit.pojo.TbSavingCard;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SavingCardMapper extends Mapper<TbSavingCard> {
}
