package com.credit.service.impl;

import com.credit.mapper.CreditCardInfoMapper;
import com.credit.mapper.CreditCardSecurityInfoMapper;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {

    @Resource
    private CreditCardSecurityInfoMapper creditCardSecurityInfoMapper;

    @Resource
    private CreditCardInfoMapper creditCardInfoMapper;

    /**
     * 查找下的所有卡号
     * @param uid
     * @return
     */
    @Override
    public List<TbCreditCardSecurityInfo> findCardidlistbyUid(Long uid) {
        TbCreditCardSecurityInfo vo = new TbCreditCardSecurityInfo();
        vo.setUid(uid);
        return creditCardSecurityInfoMapper.select(vo);
    }

    /**
     * 通过卡号ccid查找卡片信息
     * @param ccId
     * @return
     */
    @Override
    public TbCreditCardInfo findCardInfobyCcid(Long ccId) {
        TbCreditCardInfo vo = new TbCreditCardInfo();
        vo.setCcId(ccId);
        return creditCardInfoMapper.selectOne(vo);
    }

    /**
     * 获取已出账单的12个月的（年月信息）
     * @param currentYear 当前年份
     * @param yearMonthlsit 用于装12个月的年月集合
     */
    public void getYearMonth12(int currentMonth, int currentYear, List<String> yearMonthlsit) {
        for (int j = 0; j < 12; j++ ){
            currentMonth =- 1;
            if((currentMonth - 1) <= 0){
                currentMonth = 12;
                currentYear =- 1;
            }
            String m = getNum(currentMonth);
            String yearMonth =  currentYear +"年"+ m + "月";
            yearMonthlsit.add(yearMonth);
        }
    }

    /**
     * 处理日期int前面的无零的问题
     * @param num 原数
     * @return
     */
    public static String getNum(int num){
        return num > 9 ? "" + num : "0" + num;
    }

    /**
     * 1. 计算还款总额；
     * 2. 用对象装用户的所有卡片
     * @param cardIdList 所装所有卡片信息
     * @param cardlsit 用于装卡名和卡号
     * @return
     */
    public Long getaLong(List<TbCreditCardSecurityInfo> cardIdList, List<TbCreditCardInfo> cardlsit) {
        Long sum = null;
        if(!cardIdList.isEmpty()) {
            for (int i = 0; i < cardIdList.size(); i++) {
                TbCreditCardSecurityInfo vo = cardIdList.get(i);
                Long ccId = vo.getCcId();//卡号
                TbCreditCardInfo jo = this.findCardInfobyCcid(ccId);
                Long repaidAmount = jo.getRepaidAmount(); //一张卡的需还款金额
                String cardName = jo.getCardName();//卡名
                Long cid = jo.getId();
                TbCreditCardInfo po = new TbCreditCardInfo();
                po.setCcId(ccId);
                po.setCardName(cardName);
                po.setId(cid);
                cardlsit.add(po);
                sum =+ repaidAmount;//需还款总额
            }
        }else{
            sum = 0L;
        }
        return sum;
    }

    /**
     * 查找上个月的账单日
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    public String getBillday(int currentYear, int currentMonth) {
        int shangMonth;//上个月
        int shangYear = currentYear;//年份
        if((shangMonth = (currentMonth - 1))<= 0){
            shangMonth = 12;
            shangYear =- 1;
        }
        String shangmonth = getNum(shangMonth);
        return shangYear +""+ shangmonth +"16";
    }

}
