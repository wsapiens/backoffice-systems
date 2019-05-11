package com.zappos.backoffice.mapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.tsv.domain.TsvBrand;

public class BrandToTsvBrandMapperTests {

    private BrandToTsvBrandMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new BrandToTsvBrandMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMap() {
        Brand brand = new Brand(11L, "name");
        TsvBrand tsvBrand = mapper.map(brand);
        Assert.assertNotNull(tsvBrand);
        Assert.assertTrue(brand.getId().intValue() == tsvBrand.getId() && brand.getName().equals(tsvBrand.getName()));
    }

}
