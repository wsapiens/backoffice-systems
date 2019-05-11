package com.zappos.backoffice.tsv.parser;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zappos.backoffice.tsv.domain.TsvInventory;

public class TsvInventoryParserTests {

    private TsvInventoryParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new TsvInventoryParser();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParse() {
        List<TsvInventory> inventories = parser.parse(new File(getClass().getResource("/Brand_Quantity_Time_Received.tsv").getPath()));
        Assert.assertNotNull(inventories);
        Assert.assertFalse(inventories.isEmpty());
    }

}
