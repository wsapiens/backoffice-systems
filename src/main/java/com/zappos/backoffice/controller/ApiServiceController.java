package com.zappos.backoffice.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class ApiServiceController {
    Logger log = Logger.getLogger(ApiServiceController.class);
    @GetMapping(path = "/test")
    public void test() {
        log.debug("test");
    }
}
