package com.credit.service;

import com.credit.mapper.CreditCardInfoMapper;
import com.credit.mapper.CreditCardSecurityInfoMapper;
import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.utils.CodesUtils;
import com.credit.utils.NumberUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ManageService {

    @Autowired
    private CreditCardInfoMapper creditCardInfoMapper;

    @Autowired
    private CreditCardSecurityInfoMapper creditCardSecurityInfoMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    static final String MANAGE_KEY_PREFIX = "manage:code:phone:";


    /**
     * 查询客户名下的信用卡
     * @param uid
     * @return
     */
    public List<TbCreditCardInfo> queryContactCreditCard(Long uid) {

        Example example=new Example(TbCreditCardSecurityInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);
        List<TbCreditCardSecurityInfo> securityInfo = this.creditCardSecurityInfoMapper.selectByExample(example);
        List<Long> ccidList=new ArrayList();
        ccidList=securityInfo.stream().map(info -> info.getCcId()).collect(Collectors.toList());

        Example example2=new Example(TbCreditCardInfo.class);
        Example.Criteria criteria2=example2.createCriteria();

        criteria2.andIn("cc_id",ccidList);

        return this.creditCardInfoMapper.selectByExample(example2);

    }


    /**
     * 修改信用卡支付密码
     * @param oldpaypwd 旧密码
     * @param newpaypwd 新密码
     * @param creditCardId 卡号
     * @return
     */
    public Map<String, Object> alterCardPayPassword(String oldpaypwd, String newpaypwd, Long creditCardId) {

        Map<String,Object>map=new HashMap<>();

        TbCreditCardSecurityInfo card = this.creditCardSecurityInfoMapper.selectByPrimaryKey(creditCardId);

        if(!CodesUtils.validate(oldpaypwd,card.getPaypwd())){
            map.put("message","pwdError");
            return map;
        }
        String password = CodesUtils.generatePassword(newpaypwd, card.getSolt());

        TbCreditCardSecurityInfo info=new TbCreditCardSecurityInfo();
        info.setCcId(card.getCcId());
        info.setPaypwd(password);
        int i = this.creditCardSecurityInfoMapper.updateByPrimaryKeySelective(info);
        if(i<=0){
            map.put("message","alterFalse");
            return map;
        }
        map.put("message","alterTrue");
        return map;
    }


    /**
     * 重置信用卡支付密码
     * @param creditCardId
     * @param securityCode
     * @param valCode
     * @param smsCode
     * @param newPayPwd
     * @return
     */
    public Map<String, Object> resetCardPayPassword(Long creditCardId, Long securityCode, Long valCode, String smsCode, String newPayPwd) {

        Map<String,Object>map=new HashMap<>();

        TbCreditCardSecurityInfo card = this.creditCardSecurityInfoMapper.selectByPrimaryKey(creditCardId);
        if(!securityCode.equals(card.getSecurityCode())&&valCode.equals(card.getValCode())){
            map.put("message","SCodeOrVCodeError");
            return map;
        }
        String redisCode = this.redisTemplate.opsForValue().get(MANAGE_KEY_PREFIX + card.getHolderPhone());
        if(!smsCode.equals(redisCode)){
            map.put("message","smsCodeError");
            return map;
        }
        String password = CodesUtils.generatePassword(newPayPwd, card.getSolt());

        TbCreditCardSecurityInfo info=new TbCreditCardSecurityInfo();
        info.setCcId(card.getCcId());
        info.setPaypwd(password);
        int i = this.creditCardSecurityInfoMapper.updateByPrimaryKeySelective(info);
        if(i<=0){
            map.put("message","alterFalse");
            return map;
        }
        map.put("message","alterTrue");
        return map;

    }

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    public Boolean sendVerifyCode(String phone) {

        if(phone.length()!=11){
            return null;
        }
        String code = NumberUtils.generateCode(6);

        try {
            Map<String,String>msg=new HashMap<>();
            msg.put("phone",phone);
            msg.put("code",code);

            this.rabbitTemplate.convertAndSend("creditCard.sms.exchange","sms.verify.code",msg);
            this.redisTemplate.opsForValue().set(MANAGE_KEY_PREFIX+phone,code,5, TimeUnit.MINUTES);
            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据卡号或者身份证查找名下的卡片（卡片安全信息表）精准查询
     * @param creditCardId
     * @param idcard
     * @return
     */
    public List<TbCreditCardSecurityInfo> queryContactCardByCIDorIDC(Long creditCardId, String idcard) {
        Example example=new Example(TbCreditCardSecurityInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("cc_id",creditCardId).orEqualTo("id_card",idcard);
                //andLike("cc_id","%"+creditCardId+"%").orLike("id_card","%"+idcard+"%");
        return this.creditCardSecurityInfoMapper.selectByExample(example);
    }

    /**
     * 调整信用卡额度
     * @param creditCardId
     * @param creditAmount
     * @return
     */
    public Map<String, Object> alterCreditAmount(Long creditCardId, Long creditAmount) {

        Map<String,Object>map=new HashMap<>();

        TbCreditCardInfo info=new TbCreditCardInfo();
        info.setCcId(creditCardId);
        info.setCreditAmount(creditAmount);
        int i = this.creditCardInfoMapper.updateByPrimaryKeySelective(info);
        if(i<=0){
            map.put("message","alterFalse");
            return map;
        }
        map.put("message","alterTrue");
        return map;
    }
}
