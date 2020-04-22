package com.credit.service;

import com.credit.mapper.BasicInfoMapper;
import com.credit.mapper.CompanyInfoMapper;
import com.credit.mapper.ContactInfoMapper;
import com.credit.mapper.UserMapper;
import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import com.credit.pojo.TbUser;
import com.credit.utils.CodesUtils;
import com.credit.utils.NumberUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BasicInfoMapper basicInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

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
            this.redisTemplate.opsForValue().set(KEY_PREFIX+phone,code,5, TimeUnit.MINUTES);
            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    public Boolean register(TbUser user, String code){

        String redisCode=redisTemplate.opsForValue().get(KEY_PREFIX+user.getMobilePhone());
        if(!code.equals(redisCode)){
            return false;
        }

        String salt = CodesUtils.generateSalt();
        String password = CodesUtils.generatePassword(user.getUserPwd(), salt);
        user.setRegisterTime(new Date());
        user.setSolt(salt);
        user.setUserPwd(password);

        Boolean boo= this.userMapper.insert(user)!=0;
        if(boo==true){
            redisTemplate.delete(KEY_PREFIX+user.getMobilePhone());
        }
        return boo;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public TbUser login(String username,String password){
        TbUser record=new TbUser();
        record.setUserName(username);
        TbUser user = this.userMapper.selectOne(record);

        if(user==null){
            return null;
        }
        if(!CodesUtils.validate(password,user.getUserPwd())){
            return null;
        }
        return user;
    }

    /**
     * 更新密码
     * @param user
     * @param code
     * @return
     */
    public Boolean alterPassword(TbUser user,String code){

        String redisCode=redisTemplate.opsForValue().get(KEY_PREFIX+user.getMobilePhone());
        if(!code.equals(redisCode)){
            return false;
        }
        TbUser re=new TbUser();
        //通过唯一的手机号进行查找到对象
        re.setMobilePhone(user.getMobilePhone());
        TbUser tbUser = this.userMapper.selectOne(re);
        String password = CodesUtils.generatePassword(user.getUserPwd(), re.getSolt());
        tbUser.setUserPwd(password);
        return this.userMapper.insertSelective(tbUser)!=0;
    }

    /**
     * 根据uid查询基本信息
     * @param uid
     * @return
     */
    public TbBasicInfo queryBasic(int uid) {
        Example example=new Example(TbBasicInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);
        return this.basicInfoMapper.selectOneByExample(example);
    }

    /**
     *根据uid查询公司信息
     * @param uid
     * @return
     */
    public TbCompanyInfo queryCompany(int uid) {
        Example example=new Example(TbBasicInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);
        return this.companyInfoMapper.selectOneByExample(example);
    }

    /**
     *根据uid查询联系人信息
     * @param uid
     * @return
     */
    public TbContactInfo queryContact(int uid) {
        Example example=new Example(TbBasicInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);
        return this.contactInfoMapper.selectOneByExample(example);
    }

    /**
     * 更新基本信息
     * @param basicInfo
     * @return
     */
    public Boolean updateBasic(TbBasicInfo basicInfo) {
        Long uid = basicInfo.getUid();

        Example example=new Example(TbBasicInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);

        return this.basicInfoMapper.updateByExampleSelective(basicInfo,example)!=0;
    }

    /**
     * 更新公司信息
     * @param companyInfo
     * @return
     */
    public Boolean updateCompany(TbCompanyInfo companyInfo) {
        Long uid = companyInfo.getUid();

        Example example=new Example(TbBasicInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);
        return this.companyInfoMapper.updateByExampleSelective(companyInfo,example)!=0;
    }

    /**
     * 更新联系人信息
     * @param contactInfo
     * @return
     */
    public Boolean updateContact(TbContactInfo contactInfo) {
        Long uid = contactInfo.getUid();
        Example example=new Example(TbBasicInfo.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andEqualTo("uid",uid);
        return this.contactInfoMapper.updateByExampleSelective(contactInfo,example)!=0;
    }
}
