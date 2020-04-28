package com.credit.service;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;

import java.util.List;

/**
 * 1. 账单汇总
 *      1）查某用户的所有信用卡信息：显示需还款总额、最近还款日
 * 2. 卡片显示
 * 3. 月份显示
 */
public interface BaseService {

    /**
     * 查找下的所有卡号
     * @return
     */
    public List<TbCreditCardSecurityInfo> findCardidlistbyUid(Long uid);

    /**
     * 通过卡号ccid查找卡片信息
     * @return
     */
    public TbCreditCardInfo findCardInfobyCcid(Long ccId);
}
