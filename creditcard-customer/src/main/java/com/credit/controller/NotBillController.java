package com.credit.controller;

import com.credit.pojo.TbCreditCardSecurityInfo;
import com.credit.service.BillFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/")
public class NotBillController {

    @Autowired
    private BillFeignClient billFeignClient;

    @RequestMapping("/notbill")
    public String getWeiChuBill(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        /*HttpSession session = request.getSession();
        TbUser user = (TbUser) session.getAttribute("user");
        Long uid = user.getUid();*/
        Long uid = 1L;
        List<TbCreditCardSecurityInfo> ccIdList = billFeignClient.findCardIdListByUid(uid);//【通过uid的查询所有信用片安全信息】
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if(ccIdList.isEmpty()){
            out.print("<script>alert('还没有信用卡');location='index.html';</script>");
            out.flush();
            out.close();
            return null; }//没注册卡时跳回主页
        return "notbill";
    }
}
