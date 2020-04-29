package com.credit.service;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 获取已出账单的12个月的（年月信息）
     * @param currentYear 当前年份
     * @param yearMonthlsit 用于装12个月的年月集合
     */
    public void getYearMonth12(int currentMonth,int currentYear, List<String> yearMonthlsit);

    /**
     * 1. 计算还款总额；
     * 2. 用对象装用户的所有卡片
     * @param cardIdList 所装所有卡片信息
     * @param cardlsit 用于装卡名和卡号
     * @return
     */
    public Long getaLong(List<TbCreditCardSecurityInfo> cardIdList, List<TbCreditCardInfo> cardlsit);

    /**
     * 查找上个月的账单日
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    public String getBillday(int currentYear, int currentMonth);

    /**
     * 查找上上个月的（账单日+1）
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    public String getshangshangBillDate(int currentYear, int currentMonth);

    /**
     * 日期转换：String => java.util.Date
     * @param sdf 日期格式对象
     * @param shangshangBillDate 上上个月账单日的String类型
     * @return
     */
    public Date getDate(SimpleDateFormat sdf, String shangshangBillDate);
}
