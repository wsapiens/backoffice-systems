package com.zappos.backoffice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zappos.backoffice.service.BrandService;
import com.zappos.backoffice.service.InventoryService;
import com.zappos.backoffice.tsv.domain.TsvBrand;
import com.zappos.backoffice.tsv.domain.TsvInventory;
import com.zappos.backoffice.tsv.parser.TsvBrandParser;
import com.zappos.backoffice.tsv.parser.TsvInventoryParser;

@Configuration
public class ControllerTestConfig {

    @Bean
    public TsvBrandParser tsvBrandParser() {
        List<TsvBrand> list = new ArrayList<>();
        list.add(new TsvBrand(1, "name"));
        TsvBrandParser parser = mock(TsvBrandParser.class);
        when(parser.parse(any())).thenReturn(list);
        return parser;
    }

    @Bean
    public TsvInventoryParser tsvInventoryParser() {
        List<TsvInventory> list = new ArrayList<>();
        list.add(new TsvInventory(1, 20, "2018-10-04T08:01:00.0000000Z"));
        TsvInventoryParser parser = mock(TsvInventoryParser.class);
        when(parser.parse(any())).thenReturn(list);
        return parser;
    }

    @Bean
    public BrandService brandService() {
        List<TsvBrand> list = new ArrayList<>();
        list.add(new TsvBrand(1, "name"));
        BrandService service = mock(BrandService.class);
        when(service.read(any(), any(), any())).thenReturn(list);
        return service;
    }

    @Bean
    public InventoryService inventoryService() {
        List<TsvInventory> list = new ArrayList<>();
        list.add(new TsvInventory(1, 20, "2018-10-04T08:01:00.0000000Z"));
        InventoryService service = mock(InventoryService.class);
        when(service.read(any())).thenReturn(list);
        return service;
    }

    @Bean
    public FileUploadController fileUploadController() {
        return new FileUploadController();
    }
}
