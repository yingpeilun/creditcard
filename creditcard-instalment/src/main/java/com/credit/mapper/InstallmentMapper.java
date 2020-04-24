package com.credit.mapper;

import com.credit.pojo.TbInstallment;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface InstallmentMapper extends Mapper<TbInstallment> {
}
