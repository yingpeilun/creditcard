package com.credit.controller;

import com.credit.pojo.TbUser;
import com.credit.service.CUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class CUserController {

    @Autowired
    private CUserService userService;


    @GetMapping("loginPage")
    public String mian(){
        return "user/login";
    }

    @GetMapping("forgetPage")
    public String forgetPage(){
        return "user/forget";
    }

    @GetMapping("registerPage")
    public String registerPage(){
        return "user/register";
    }

    @GetMapping("homePage")
    public String homePage(){return "homePage";}





    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Map<String,String>> login(
            @RequestParam("userName")String userName,
            @RequestParam("userPwd")String userPwd,
            HttpServletRequest request
    ){
        TbUser tbuser=this.userService.login(userName,userPwd);
        Map<String,String>map=new HashMap<>();
        if(tbuser==null){
            map.put("message","fail");
            return ResponseEntity.ok(map);
        }
        request.getSession().setAttribute("user",tbuser);
        map.put("message","success");
        return ResponseEntity.ok(map);
    }


    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Map<String,String>> register(TbUser user,
            @RequestParam("code")String code
    ){
        System.out.println("user实体："+user.getUserName());
        Boolean boo=this.userService.register(user,code);
        Map<String,String>map=new HashMap<>();
        if(boo!=true){
            map.put("message","fail");
            return ResponseEntity.ok(map);
        }
        map.put("message","success");
        return ResponseEntity.ok(map);
    }


    /**
     * 用户验证手机号和发送短信
     * @param phone
     * @return
     */
    @PostMapping("sendCode")
    @ResponseBody
    public Map<String,String> sendSMS(@RequestParam("mobilePhone") String phone){
        Map<String,String>map=this.userService.sendSMS(phone);
        return map;
    }


}
