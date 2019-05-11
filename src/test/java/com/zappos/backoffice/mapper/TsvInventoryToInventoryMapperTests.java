package com.zappos.backoffice.mapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.tsv.domain.TsvInventory;

public class TsvInventoryToInventoryMapperTests {

    private TsvInventoryToInventoryMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new TsvInventoryToInventoryMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMap() {
        TsvInventory tsvInventory = new TsvInventory(1, 12, "2018-10-07T07:07:00.0000000Z");
        Inventory inventory = mapper.map(tsvInventory);
        Assert.assertNotNull(inventory);
        Assert.assertTrue(inventory.getBrandId().intValue() == tsvInventory.getBrandId() && inventory.getQuantity() == tsvInventory.getQuantity());
    }

}
