package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class Login{

    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    @ResponseBody
    @RequestMapping("/index")
    public String index(){
        return "拦截器index，实施拦截，打印出结果";
    }
}
