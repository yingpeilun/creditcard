package com.credit.mapper;

import com.credit.pojo.TbUser;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<TbUser> {

}
