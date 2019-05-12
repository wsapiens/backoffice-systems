package com.zappos.backoffice.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zappos.backoffice.service.BrandService;
import com.zappos.backoffice.view.model.BrandStatus;

@RestController
@RequestMapping(value = "/service/v1")  // service version 1
public class WebServiceController {
    Logger log = Logger.getLogger(WebServiceController.class);

    @Autowired
    private BrandService brandService;

    @GetMapping(path = "/brands")
    public BrandStatus readBrands(@RequestParam(required = false) Long id,
                                  @RequestParam(required = false) String name) {
        log.debug("read brands");
        BrandStatus status = new BrandStatus();
        status.getBrands().addAll(brandService.read(id, name, null));
        return status;
    }
}
