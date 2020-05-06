package com.credit.service;

import com.credit.cilent.ApplyClient;
import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class ApplyCardService {

    @Resource
    private ApplyClient applyClient;

    @Autowired
    private RedisTemplate redisTemplate;

    static final String UID_PREFIX = "user:uid:code:";
    /**
     * 提交基本信息，并跳转第二个页面
     * @param basicInfo
     * @return
     */
    public Boolean applyCardSecond(TbBasicInfo basicInfo) {
        Long uid = basicInfo.getUid();
        try {
            this.redisTemplate.opsForValue().set(UID_PREFIX+uid+"basicInfo",basicInfo,30, TimeUnit.MINUTES);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 提交公司信息，并跳转第三个页面
     * @param companyInfo
     * @return
     */
    public Boolean applyCardThird(TbCompanyInfo companyInfo) {
        Long uid = companyInfo.getUid();
        try {
            this.redisTemplate.opsForValue().set(UID_PREFIX+uid+"companyInfo",companyInfo,30, TimeUnit.MINUTES);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 提交联系人信息，并跳转到主页
     * @param contactInfo
     * @return
     */
    @Transactional
    public Boolean applyCardEnd(TbContactInfo contactInfo) {
        Long uid = contactInfo.getUid();
        TbBasicInfo basicInfo = (TbBasicInfo)this.redisTemplate.opsForValue().get(UID_PREFIX + uid + "basicInfo");
        TbCompanyInfo companyInfo=(TbCompanyInfo)this.redisTemplate.opsForValue().get(UID_PREFIX+uid+"companyInfo");

        try {
            this.applyClient.insertBasic(basicInfo);
            this.applyClient.insertCompany(companyInfo);
            this.applyClient.insertContact(contactInfo);

        } catch (Exception e) {
            throw new RuntimeException("插入异常，回滚");
        }
        return true;
    }
}
