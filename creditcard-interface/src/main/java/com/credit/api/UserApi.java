package com.credit.api;

import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import com.credit.pojo.TbUser;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("user")
public interface UserApi {

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    public Void sendVerifyCode(@RequestParam("phone") String phone);

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public Boolean register(@Valid @RequestBody TbUser user, @RequestParam("code")String code);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public TbUser login(
            @RequestParam("username")String username,
            @RequestParam("password")String password);

    /**
     * 更新密码
     * @param user
     * @param code
     * @return
     */
    @PostMapping("alterPassword")
    public Boolean alterPassword(TbUser user,@RequestParam("code")String code);

    /**
     * 根据uid查询基本信息
     * @param uid
     * @return
     */
    @GetMapping("queryBasic")
    public TbBasicInfo queryBasic(@RequestParam("uid")int uid);

    /**
     * 更新基本信息
     * @param basicInfo
     * @return
     */
    @PostMapping("updateBasic")
    public Boolean updateBasic(TbBasicInfo basicInfo);

    /**
     *根据uid查询公司信息
     * @param uid
     * @return
     */
    @GetMapping("queryCompany")
    public TbCompanyInfo queryCompany(@RequestParam("uid")int uid);

    /**
     * 更新公司信息
     * @param companyInfo
     * @return
     */
    @PostMapping("updateCompany")
    public Boolean updateCompany(TbCompanyInfo companyInfo);

    /**
     *根据uid查询联系人信息
     * @param uid
     * @return
     */
    @GetMapping("queryContact")
    public TbContactInfo queryContact(@RequestParam("uid")int uid);

    /**
     * 更新联系人信息
     * @param contactInfo
     * @return
     */
    @PostMapping("updateContact")
    public Boolean updateBasic(TbContactInfo contactInfo);
}
