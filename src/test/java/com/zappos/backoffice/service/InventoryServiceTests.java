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

import com.zappos.backoffice.tsv.domain.TsvInventory;

@ContextConfiguration(classes = ServiceTestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryServiceTests {

    @Autowired
    private InventoryService inventoryService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRead() {
        List<TsvInventory> inventories = inventoryService.read(1L);
        Assert.assertFalse(inventories.isEmpty());
        inventories = inventoryService.read(null);
        Assert.assertFalse(inventories.isEmpty());
    }

    @Test
    public void testSave() {
        List<TsvInventory> list = new ArrayList<>();
        list.add(new TsvInventory(1, 23, "2018-10-12T07:05:00.0000000Z"));
        inventoryService.save(list);
    }

}
