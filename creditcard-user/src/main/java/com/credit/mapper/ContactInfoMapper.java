package com.credit.mapper;

import com.credit.pojo.TbContactInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ContactInfoMapper extends Mapper<TbContactInfo> {
}
