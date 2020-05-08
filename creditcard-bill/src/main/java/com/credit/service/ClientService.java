package com.credit.service;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;

import java.util.List;

public interface ClientService {

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

    /**
     * 查询所有信用卡
     * @return List<TbCreditCardInfo>
     */
    public List<TbCreditCardInfo> findallTbCreditCardInfo();

    /**
     * 更新信用卡的账单日、还款日等
     * @param vo TbCreditCardInfo对象
     * @return
     */
    public boolean updateBillDateAndRepayDate(TbCreditCardInfo vo);
}
