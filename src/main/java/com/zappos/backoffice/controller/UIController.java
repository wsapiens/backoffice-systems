package com.zappos.backoffice.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ComponentScan(basePackages = "com.zappost.bakcoffice.controller")
public class UIController {
    @RequestMapping("/view")
    public String brand() {
        return "brand";
    }
}
