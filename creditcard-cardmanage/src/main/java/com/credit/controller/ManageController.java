package com.credit.controller;

import com.credit.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManageController {

    @Autowired
    private ManageService manageService;
}
