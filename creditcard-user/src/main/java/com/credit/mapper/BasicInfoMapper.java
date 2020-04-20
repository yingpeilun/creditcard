package com.credit.mapper;

import com.credit.pojo.TbBasicInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BasicInfoMapper extends Mapper<TbBasicInfo> {
}
