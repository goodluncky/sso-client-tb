package com.goodluncky.controller;

import com.goodluncky.util.SSOClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping("/taobao")
    public String index(Model model){
        model.addAttribute("logoutURL", SSOClientUtil.getServerLogOutUrl());
        return "taobao";
    }

    //注销请求
    @RequestMapping("/logOut")
    public void logOut(HttpSession session){
        session.invalidate();
    }
}
