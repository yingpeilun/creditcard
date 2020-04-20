package com.credit.mapper;

import com.credit.pojo.TbCompanyInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CompanyInfoMapper extends Mapper<TbCompanyInfo> {
}
