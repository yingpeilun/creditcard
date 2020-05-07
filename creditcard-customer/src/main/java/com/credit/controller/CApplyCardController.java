package com.credit.controller;

import com.credit.pojo.TbBasicInfo;
import com.credit.pojo.TbCompanyInfo;
import com.credit.pojo.TbContactInfo;
import com.credit.service.CApplyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("apply")
public class CApplyCardController {

    @Autowired
    private CApplyCardService applyCardService;

    /**
     * 进入主页面
     * @return
     */
    @GetMapping("homePage")
    public String index(){
        System.out.println("我进来了");
        return "homePage";
    }

    /**
     * 点击办卡，跳转的第一个页面
     * @return
     */
    @GetMapping("applyCard01.html")
    public String applyCardFirst(){
        System.out.println("进入办卡第一页");
        return "apply/applyCard01";
    }

    /**
     * 提交基本信息，并跳转第二个页面
     * @param basicInfo
     * @return
     */
    @PostMapping("applyCard02.html")
    public String applyCardSecond(TbBasicInfo basicInfo){
        System.out.println("进入办卡第二页");
        Boolean boo=this.applyCardService.applyCardSecond(basicInfo);
        if(!boo){
            return "error";
        }
        return "apply/applyCard02";
    }

    /**
     * 提交公司信息，并跳转第三个页面
     * @param companyInfo
     * @return
     */
    @PostMapping("applyCard03.html")
    public String applyCardThird(TbCompanyInfo companyInfo){
        System.out.println("进入办卡第三页");
        Boolean boo=this.applyCardService.applyCardThird(companyInfo);
        if(!boo){
            return "error";
        }
        return "apply/applyCard03";
    }

    /**
     * 提交联系人信息，并跳转到主页
     * @param contactInfo
     * @return
     */
    @PostMapping("applyCardEnd.html")
    public String applyCardEnd(TbContactInfo contactInfo){
        System.out.println("进入办卡第最后页");
        Boolean boo=this.applyCardService.applyCardEnd(contactInfo);
        if(!boo){
            return "error";
        }
        return "homePage";
    }
}
