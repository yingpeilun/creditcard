package com.credit.service;

import com.credit.mapper.CreditCardInfoMapper;
import com.credit.mapper.UserCreditMapper;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbUserCredit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FunctionService {

    @Autowired
    private CreditCardInfoMapper creditCardInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserCreditMapper userCreditMapper;


    /**
     * 信用卡消费
     * @param ccId 信用卡卡号
     * @param money 消费金额
     * @param payInfo 消费描述
     * @param getMoneyCardId 收款账号
     * @param moneyType 币种
     * @return Map
     */
    public Map<String,Object> consume(Long ccId,Long money,String payInfo,String getMoneyCardId,String moneyType){

        Map<String,Object>msg=new HashMap<>();
        TbCreditCardInfo info = this.creditCardInfoMapper.selectByPrimaryKey(ccId);
        if(info.getRemainAmount()<money){
            msg.put("message","余额不足");
            return msg;
        }
        Long a=info.getRemainAmount()-money;
        Long b=info.getConAmount()+money;
        TbCreditCardInfo cci=new TbCreditCardInfo();
        cci.setRemainAmount(a);
        cci.setConAmount(b);
        cci.setCcId(ccId);
        int i = this.creditCardInfoMapper.updateByPrimaryKeySelective(cci);
        if(i<=0){
            msg.put("message","false");
            return msg;
        }
        Map<String,String>map=new HashMap<>();
        map.put("money",money.toString());
        map.put("payInfo",payInfo);
        map.put("getMoneyCardId",getMoneyCardId);
        map.put("moneyType",moneyType);
        map.put("ccId",ccId.toString());
        rabbitTemplate.convertAndSend("creditCard.bill.exchange","bill.consume.msg",map);
        msg.put("message","true");
        return msg;
    }


    /**
     * 定时更新到账单日的信用卡信息
     * @param repayDateNum
     * @param billDateNum
     */
    public void scheduledUpdateCard(String repayDateNum,Long billDateNum){
        Example example=new Example(TbCreditCardInfo.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("repay_date_num",billDateNum);

        List<TbCreditCardInfo> infos = this.creditCardInfoMapper.selectByExample(example);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date repaydate = null;// 将字符串转化为时间格式
        Date billdate = null;
        try {
            repaydate = sdf.parse(repayDateNum);
            billdate = sdf.parse(billDateNum.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar repaycalendar = Calendar.getInstance(); // 得到日历
        Calendar billcalendar = Calendar.getInstance();
        repaycalendar.setTime(repaydate);// 把开始日期赋给日历
        billcalendar.setTime(billdate);
        repaycalendar.add(Calendar.MONTH, 1); // 设置为num月
        billcalendar.add(Calendar.MONTH, 1); // 设置为num月
        Date repayResultDate = repaycalendar.getTime(); // 得到时间
        Date billResultDate = billcalendar.getTime(); // 得到时间
        String redateformat = sdf.format(repayResultDate);
        String bidateformat = sdf.format(billResultDate);

        infos.forEach(tbCardInfo -> {
            TbCreditCardInfo card=new TbCreditCardInfo();
            card.setCcId(tbCardInfo.getCcId());
            card.setBillDate(billResultDate);
            card.setBillDateNum(Long.valueOf(bidateformat));
            card.setRepayDate(repayResultDate);
            card.setRepayDateNum(Long.valueOf(redateformat));

            Long conAmount = tbCardInfo.getConAmount();//本期消费金额
            Long repaidAmount = tbCardInfo.getRepaidAmount();//需还款金额
            card.setConAmount(0L);
            card.setRepaidAmount(repaidAmount+conAmount);
            card.setMinpaidAmount((repaidAmount+conAmount)/10);

            TbUserCredit userc=new TbUserCredit();
            userc.setCcId(tbCardInfo.getCcId());
            TbUserCredit credit = this.userCreditMapper.selectOne(userc);
            if(credit.getOverdueAmount()>0){
                card.setLateFee(credit.getOverdueAmount()*5/100);
            }

            this.creditCardInfoMapper.updateByPrimaryKeySelective(card);
        });
    }

    /**
     * 获取账单日
     * @param time
     * @return
     */
    public Long queryBillDate(String time){

        Example example=new Example(TbCreditCardInfo.class);
        Example.Criteria criteria=example.createCriteria();
        Long value = Long.valueOf(time);
        criteria.andEqualTo("repay_date_num",value);

        List<TbCreditCardInfo> infos = this.creditCardInfoMapper.selectByExample(example);
        if(infos != null && infos.size() >0){
            return infos.get(0).getBillDateNum();
        }
        return null;
    }


    /**
     * 查找逾期金额大于0的所有卡号
     * @return
     */
    public List<TbUserCredit>queryUserCredit(){

        Example example=new Example(TbCreditCardInfo.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andGreaterThan("overdue_amount",0);

        return this.userCreditMapper.selectByExample(example);
    }

    /**
     * 定时更新利息
     */
    public void scheduledUpdateInt(){
        List<TbUserCredit> credits = this.queryUserCredit();
        credits.forEach(credit -> {
            Long ccId = credit.getCcId();
            Long overdueAmount = credit.getOverdueAmount();

            TbCreditCardInfo info=new TbCreditCardInfo();
            info.setCcId(ccId);
            Long value=overdueAmount*5/10000;
            info.setInterest(value);
            this.creditCardInfoMapper.updateByPrimaryKeySelective(info);
            Map<String,String>map=new HashMap<>();
            map.put("interest",value.toString());
            map.put("ccId",ccId.toString());
            map.put("payInfo","利息");
            map.put("getMoneyCardId","中信银行");
            map.put("moneyType","人民币");
            rabbitTemplate.convertAndSend("creditCard.bill.exchange","bill.consume.msg",map);
        });
    }

    /**
     * 更新信用卡的剩余额度和需还款额度
     * @param ccId
     * @param repayMoney
     */
    public void updateCardAmount(String ccId,String repayMoney){
        TbCreditCardInfo ci=new TbCreditCardInfo();
        ci.setCcId(Long.valueOf(ccId));
        TbCreditCardInfo cardInfo = this.creditCardInfoMapper.selectOne(ci);
        ci.setId(cardInfo.getId());
        ci.setRepaidAmount(cardInfo.getRepaidAmount()-Long.valueOf(repayMoney));
        ci.setRemainAmount(cardInfo.getRemainAmount()-Long.valueOf(repayMoney));
        this.creditCardInfoMapper.updateByPrimaryKeySelective(ci);
    }
}
