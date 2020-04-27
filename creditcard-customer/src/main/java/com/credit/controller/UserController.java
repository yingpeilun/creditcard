package com.credit.controller;

import com.credit.pojo.TbUser;
import com.credit.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/index.thml")
    private String mian(){
        return "index";
    }

    @PostMapping("/index")
    private String login( @RequestParam("userName")String userName,
                          @RequestParam("userPwd")String userPwd){
        System.out.println("进来了消费者login");
        System.out.println("name:"+userName);
        System.out.println("pwd:"+userPwd);
        TbUser tbuser=userFeignClient.login(userName,userPwd);
        if(tbuser==null){
            return "index";
        }
        return "mian";
    }
}
