package com.credit.controller;

import com.credit.pojo.TbCreditCardInfo;
import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.service.BaseService;
import com.credit.service.BillService;
import com.credit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 客户管理：主页
 */
@Controller
@RequestMapping("/")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * 查找用户下的所有卡号(用户安全信息表)
     * @param uid
     * @return
     */
    @PostMapping("/client/findCardIdListByUid")
    @ResponseBody
    public List<TbCreditCardSecurityInfo> findCardIdListByUid(@RequestParam("uid") Long uid){
        return clientService.findCardidlistbyUid(uid);
    }

    /**
     * 通过卡号ccid查找卡片信息（信用卡信息表）
     * @param ccId
     * @return
     */
    @PostMapping("/client/findCardInfoByCcid")
    @ResponseBody
    public TbCreditCardInfo findCardInfoByCcid(@RequestParam("ccId")Long ccId){
        return clientService.findCardInfobyCcid(ccId);
    }

}
