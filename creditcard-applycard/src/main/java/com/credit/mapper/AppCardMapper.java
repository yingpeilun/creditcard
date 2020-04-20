package com.credit.mapper;

import com.credit.pojo.TbAppCardStatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AppCardMapper extends Mapper<TbAppCardStatus> {
}
