package com.credit.service;

import com.credit.cilent.UserClient;
import com.credit.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CUserService {

    @Resource
    private UserClient userClient;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public TbUser login(String username, String password) {
        TbUser tbUser = this.userClient.login(username,password);
        return tbUser;
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    public Boolean register(TbUser user, String code) {
        Boolean boo = this.userClient.register(user, code);
        return boo;
    }

    /**
     * 用户验证手机号和发送短信
     * @param phone
     * @return
     */
    public Map<String, String> sendSMS(String phone) {
        System.out.println("手机号:"+phone);
        TbUser user = this.userClient.validPhone(phone);
        Map<String, String>map=new HashMap<>();
        if(user!=null){
            System.out.println("userService:空");
            map.put("message","fail");
            return map;
        }
        this.userClient.sendVerifyCode(phone);
        System.out.println("userService:已发送");
        map.put("message","success");
        return map;
    }
}
