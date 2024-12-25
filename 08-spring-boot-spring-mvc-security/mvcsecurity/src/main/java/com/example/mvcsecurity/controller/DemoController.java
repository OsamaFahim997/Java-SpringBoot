package com.example.mvcsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/leader")
    public String showLeaderPage() {
        return "leader";
    }

    @GetMapping("/system")
    public String showAdminPage() {
        return "admin";
    }

}
