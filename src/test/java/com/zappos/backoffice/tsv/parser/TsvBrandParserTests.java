package com.zappos.backoffice.tsv.parser;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zappos.backoffice.tsv.domain.TsvBrand;

public class TsvBrandParserTests {

    private TsvBrandParser parser;

    @Before
    public void setUp() throws Exception {
        parser =  new TsvBrandParser();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Parse brand data dump file regardless of column order
     * To find column order, the dump file must have header line
     */
    @Test
    public void testParse() {
        // column order - id    name
        List<TsvBrand> brands = parser.parse(new File(getClass().getResource("/Brands.tsv").getPath()));
        Assert.assertNotNull(brands);
        Assert.assertFalse(brands.isEmpty());

        // different column order - name   id
        // it should generate same list of objects for value wise
        // Lombok @Data will implement hashcode by all the fields as default.
        List<TsvBrand> brands1 = parser.parse(new File(getClass().getResource("/Brands1.tsv").getPath()));
        Assert.assertTrue(brands1.stream().allMatch(b1 -> brands.contains(b1) ));
    }

}
