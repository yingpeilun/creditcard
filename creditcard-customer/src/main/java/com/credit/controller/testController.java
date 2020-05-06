package com.credit.controller;

import com.credit.cilent.UserClient;
import com.credit.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class testController {

    @Autowired
    private UserClient userClient;

    @GetMapping("userlogin")
    @ResponseBody
    public String getUser(
            @RequestParam("name")String name,
            @RequestParam("pass") String pass,
            HttpServletRequest request
    ){
        System.out.println("进来了");
        String msg="";
        TbUser login = this.userClient.login(name, pass);
        if(login==null){
            System.out.println("是空的");
            return msg="msg:空";
        }
        request.getSession().setAttribute("user",login);
        System.out.println("不是空的");
        System.out.println(login.getUserName());
        msg="msg:不是空";
        return msg;
    }

    @GetMapping("user/{name}")
    @ResponseBody
    public String sss(@PathVariable("name")String name){
        return name;
    }


    @PostMapping("register")
    @ResponseBody
    public String register(@Valid TbUser user,@RequestParam("code")String code){
        System.out.println("name:"+user.getUserName());
        System.out.println("pwd:"+user.getUserPwd());
        Boolean register = this.userClient.register(user, code);
        System.out.println("register:"+register);
        String msg="true";
        return msg;
    }
}
