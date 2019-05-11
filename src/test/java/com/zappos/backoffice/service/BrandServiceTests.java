package com.zappos.backoffice.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zappos.backoffice.tsv.domain.TsvBrand;

@ContextConfiguration(classes = ServiceTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BrandServiceTests {

    @Autowired
    private BrandService brandService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadAll() {
        List<TsvBrand> tsvBrands = brandService.read(null, null, null);
        Assert.assertFalse(tsvBrands.isEmpty());
    }

    @Test
    public void testReadOne() {
        List<TsvBrand> tsvBrands = brandService.read(1L, null, null);
        Assert.assertFalse(tsvBrands.isEmpty());

        tsvBrands = brandService.read(null, "name", null);
        Assert.assertFalse(tsvBrands.isEmpty());
    }

    @Test
    public void testSaveMultiple() {
        List<TsvBrand> list = new ArrayList<>();
        list.add(new TsvBrand(1, "name"));
        list.add(new TsvBrand(2, "name2"));
        brandService.save(list);
    }

    @Test
    public void testSaveOne() {
        brandService.save(new TsvBrand(1, "name"));
    }

    @Test
    public void testDeleteMultiple() {
        List<TsvBrand> list = new ArrayList<>();
        list.add(new TsvBrand(1, "name"));
        list.add(new TsvBrand(2, "name2"));
        brandService.delete(list);
    }

    @Test
    public void testDeleteOne() {
        brandService.delete(1L, null);
        brandService.delete(null, "name");
    }
}
