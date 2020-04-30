package com.credit.service.impl;

import com.credit.mapper.CreditCardInfoMapper;
import com.credit.mapper.CreditCardSecurityInfoMapper;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {

    @Resource
    private CreditCardSecurityInfoMapper creditCardSecurityInfoMapper;

    @Resource
    private CreditCardInfoMapper creditCardInfoMapper;

    /**
     * 查找用户下的所有卡号
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
//-----------------------------------以下是其他业务方法-------------------------------------------
    /**
     * 获取已出账单的12个月的（年月信息）
     * @param currentMonth 当年月份
     * @param currentYear 当前年份
     * @param yearMonthlsit 用于装12个月的年月集合
     */
    public void getYearMonth12(int currentMonth, int currentYear, List<String> yearMonthlsit) {
        int month = currentMonth;
        int year = currentYear;
        for (int j = 0; j < 12; j++ ){
            month =- 1;
            if((month - 1) <= 0){
                month = 12;
                year =- 1;
            }
            String m = getNum(month);
            String yearMonth =  year +"年"+ m + "月";
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
                Long cid = jo.getId();//获取卡片id主键（cid）

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
        int shangMonth = currentMonth - 1;//上个月
        int shangYear = currentYear;//年份
        if(shangMonth <= 0){
            shangMonth = 12;
            shangYear =- 1;
        }
        String shangmonth = getNum(shangMonth);
        return shangYear +""+ shangmonth +"16";
    }

    /**
     * 查找上上个月的（账单日+1）
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    public String getshangshangBillDate(int currentYear, int currentMonth) {
        int shangshangMonth = currentMonth - 2;//上上个月
        int year_1 = currentYear;//年份
        if(shangshangMonth<= 0){
            shangshangMonth = 12;
            year_1 =- 1;
        }
        String shangshangmonth = getNum(shangshangMonth);
        return year_1 +""+ shangshangmonth +"17";
    }

    /**
     * 日期转换：String => java.util.Date
     * @param Date 上上个月账单日的String类型
     * @return Date
     */
    public Date getDate(String Date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date shangshangbilldate1 = null;
        try {
            shangshangbilldate1 = sdf.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shangshangbilldate1;
    }

    /**
     * 查找上个月的（账单日+1）
     * @param currentYear 当前年份
     * @param currentMonth 当前月份
     * @return String
     */
    public String getshangBillDate_1(int currentYear, int currentMonth) {
        int shangMonth = currentMonth - 1;//上个月
        int year_2 = currentYear;//年份
        if(shangMonth<= 0){
            shangMonth = 12;
            year_2 =- 1;
        }
        String shangshangmonth = getNum(shangMonth);
        return year_2 +""+ shangshangmonth +"17";
    }
}
