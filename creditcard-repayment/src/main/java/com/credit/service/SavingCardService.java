package com.credit.service;

import com.credit.mapper.SavingCardMapper;
import com.credit.pojo.TbSavingCard;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SavingCardService {

    @Autowired
    private SavingCardMapper savingCardMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public Map<String,String> repayMoney(Long ccId, Long scId, Long repayMoney){
        TbSavingCard sc=new TbSavingCard();
        sc.setScId(scId);
        TbSavingCard savingCard = this.savingCardMapper.selectOne(sc);
        String bankName = savingCard.getBankName();//银行名
        Long singleQuota = savingCard.getSingleQuota();//单笔限额
        Long totalAmount = savingCard.getTotalAmount();//余额
        String cuName = savingCard.getCuName();//户头名

        Map<String,String>map=new HashMap<>();
        if(repayMoney>singleQuota){
            map.put("message","超过单笔还款额");
            return map;
        }
        if(totalAmount<repayMoney){
            map.put("message","余额不足");
            return map;
        }

        Long yue=totalAmount-repayMoney;
        Example example=new Example(TbSavingCard.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("sc_id",scId);
        List<TbSavingCard> savingCards = this.savingCardMapper.selectByExample(example);
        savingCards.forEach(tbSavingCard -> {
            tbSavingCard.setTotalAmount(yue);
            this.savingCardMapper.updateByExampleSelective(tbSavingCard,example);
        });
        //消息列队
        Map<String,String>msg=new HashMap<>();
        msg.put("bankName",bankName);//银行名
        msg.put("cuName",cuName);//户头名
        msg.put("repayMoney",repayMoney.toString());//还款金额
        msg.put("ccId",ccId.toString());//信用卡卡号
        msg.put("scId",scId.toString());//储蓄卡卡号
        this.rabbitTemplate.convertAndSend("creditCard.repay.exchange","repay.money.msg",msg);

        map.put("message","还款成功");
        return map;
    }
}
