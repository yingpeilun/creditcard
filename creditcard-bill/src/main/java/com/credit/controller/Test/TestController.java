package com.credit.controller.Test;

import com.credit.pojo.TbHistoryEverybill;
import com.credit.service.TestService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class TestController {
    @RequestMapping("/")
    public String Test1(){
        return "test1";
    }

    @Autowired
    private TestService billService;

    @RequestMapping("/test2")
    public String Test2(
      Model model,
      @RequestParam(value = "page",defaultValue = "1")Integer page,
      @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize)
    {
        PageInfo<TbHistoryEverybill> pageInfo = billService.queryPage(page, pageSize);
        model.addAttribute("pageinfo",pageInfo);
        return "everybill_test2";
    }
}
