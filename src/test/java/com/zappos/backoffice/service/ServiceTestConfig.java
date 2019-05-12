package com.zappos.backoffice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.database.repository.BrandRepository;
import com.zappos.backoffice.database.repository.InventoryRepository;

/**
 * Configuration for providing mocked repository to service layer testing
 * @author spark
 *
 */
@Configuration
public class ServiceTestConfig {

    @Bean
    public BrandRepository brandRepository() {
        List<Brand> list = new ArrayList<>();
        list.add(new Brand(1L, "name"));
        BrandRepository repository = mock(BrandRepository.class);
        when(repository.findAll()).thenReturn(list);
        when(repository.getOne(any())).thenReturn(new Brand(1L, "name"));
        when(repository.findByName(any())).thenReturn(new Brand(1L, "name"));
        return repository;
    }

    @Bean
    public InventoryRepository inventoryRepository() {
        Inventory inventory = new Inventory();
        inventory.setBrand(new Brand(1L, "name"));
        inventory.setQuantity(22);
        inventory.setReceivedTime(new Date());
        List<Inventory> list = new ArrayList<>();
        list.add(inventory);
        InventoryRepository repository = mock(InventoryRepository.class);
        when(repository.findAll()).thenReturn(list);
        when(repository.findByBrandId(any())).thenReturn(list);
        return repository;
    }

    @Bean
    public BrandService brandService() {
        return new BrandService();
    }

    @Bean
    public InventoryService inventoryService() {
        return new InventoryService();
    }
}
