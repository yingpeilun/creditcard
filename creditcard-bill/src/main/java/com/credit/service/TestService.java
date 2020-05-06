package com.credit.service;

import com.credit.mapper.HistoryEveryBillMapper;

import com.credit.pojo.TbHistoryEverybill;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private HistoryEveryBillMapper everybillMapper;

    /**
     * 查询所有每笔交易
     * @return
     */
    public List<TbHistoryEverybill> getEverybill(){
        return everybillMapper.selectAll();
    }

    /**
     * pagehelper插件分页查询每笔交易
     * @param pageNo 第几页数
     * @param pageSize  数据有几行
     * @return
     */
    public PageInfo<TbHistoryEverybill> queryPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbHistoryEverybill> list = this.getEverybill();
        return new PageInfo<>(list);
    }
}
