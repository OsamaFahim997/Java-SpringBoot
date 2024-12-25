package com.example.mvcsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("showLoginPage")
    public String showLoginPage() {
        return "fancy-login";
    }

    @GetMapping("accessDenied")
    public String showAccessDeniedPage() {
        return "accessDenied";
    }

}
