package com.credit.controller;

import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import com.credit.pojo.TbUser;
import com.credit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserComtroller {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone) {
        Boolean boo = this.userService.sendVerifyCode(phone);
        if(!boo){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody TbUser user, @RequestParam("code")String code){
        Boolean boo = this.userService.register(user, code);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<TbUser> login(
            @RequestParam("username")String username,
            @RequestParam("password")String password){
        TbUser user = this.userService.login(username, password);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 更新密码
     * @param user
     * @param code
     * @return
     */
    @PostMapping("alterPassword")
    public ResponseEntity<Boolean> alterPassword(TbUser user,
            @RequestParam("code")String code
    ){
        Boolean boo = this.userService.alterPassword(user, code);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     * 根据uid查询基本信息
     * @param uid
     * @return
     */
    @GetMapping("queryBasic")
    public ResponseEntity<TbBasicInfo> queryBasic(@RequestParam("uid")int uid){
        TbBasicInfo basicInfo = this.userService.queryBasic(uid);
        if(basicInfo==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(basicInfo);
    }

    /**
     * 更新基本信息
     * @param basicInfo
     * @return
     */
    @PostMapping("updateBasic")
    public ResponseEntity<Boolean> updateBasic(TbBasicInfo basicInfo){
        Boolean boo = this.userService.updateBasic(basicInfo);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     *根据uid查询公司信息
     * @param uid
     * @return
     */
    @GetMapping("queryCompany")
    public ResponseEntity<TbCompanyInfo> queryCompany(@RequestParam("uid")int uid){
        TbCompanyInfo companyInfo = this.userService.queryCompany(uid);
        if(companyInfo==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(companyInfo);
    }

    /**
     * 更新公司信息
     * @param companyInfo
     * @return
     */
    @PostMapping("updateCompany")
    public ResponseEntity<Boolean> updateCompany(TbCompanyInfo companyInfo){
        Boolean boo = this.userService.updateCompany(companyInfo);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    /**
     *根据uid查询联系人信息
     * @param uid
     * @return
     */
    @GetMapping("queryContact")
    public ResponseEntity<TbContactInfo> queryContact(@RequestParam("uid")int uid){
        TbContactInfo contactInfo = this.userService.queryContact(uid);
        if(contactInfo==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(contactInfo);
    }

    /**
     * 更新联系人信息
     * @param contactInfo
     * @return
     */
    @PostMapping("updateContact")
    public ResponseEntity<Boolean> updateContact(TbContactInfo contactInfo){
        Boolean boo = this.userService.updateContact(contactInfo);
        if(!boo){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

}
