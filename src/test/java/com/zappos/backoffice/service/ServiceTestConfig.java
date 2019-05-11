package com.zappos.backoffice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.database.repository.BrandRepository;

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
    public BrandService brandService() {
        return new BrandService();
    }
}
