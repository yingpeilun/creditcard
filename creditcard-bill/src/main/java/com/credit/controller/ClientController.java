package com.credit.controller;

import com.credit.service.BaseService;
import com.credit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户管理：主页
 */
@Controller
@RequestMapping("/")
public class ClientController {

    @Autowired
    private BaseService baseService;
    @Autowired
    private ClientService clientService;



}
