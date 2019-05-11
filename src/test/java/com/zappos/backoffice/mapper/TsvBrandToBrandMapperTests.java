package com.zappos.backoffice.mapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.tsv.domain.TsvBrand;

public class TsvBrandToBrandMapperTests {

    private TsvBrandToBrandMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new TsvBrandToBrandMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMap() {
        TsvBrand tsvBrand = new TsvBrand(3, "name");
        Brand brand = mapper.map(tsvBrand);
        Assert.assertNotNull(brand);
        Assert.assertTrue(brand.getId().intValue() == tsvBrand.getId() && brand.getName().equals(tsvBrand.getName()));
    }

}
