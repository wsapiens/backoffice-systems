package com.zappos.backoffice.mapper;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.tsv.domain.TsvInventory;

public class InventoryToTsvInventoryMapperTests {

    private InventoryToTsvInventoryMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new InventoryToTsvInventoryMapper();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Inventory inventory = new Inventory();
        inventory.setBrandId(1L);
        inventory.setQuantity(20);
        inventory.setReceivedTime(new Date());
        TsvInventory tsvInventory = mapper.map(inventory);
        Assert.assertNotNull(tsvInventory);
        Assert.assertTrue(tsvInventory.getBrandId() == inventory.getBrandId().intValue() && tsvInventory.getQuantity() == inventory.getQuantity());
    }

}
