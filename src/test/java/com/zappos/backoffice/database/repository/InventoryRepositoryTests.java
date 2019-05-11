package com.zappos.backoffice.database.repository;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.database.model.Inventory;

/**
 * This is testing InventoryRepository by in-memory database 
 * @author spark
 *
 */
@ContextConfiguration(classes = { RepositoryTestConfig.class })
@RunWith(SpringRunner.class)
@Transactional
@DirtiesContext
public class InventoryRepositoryTests {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    private Brand brand;
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        brand = new Brand();
        brand.setName("name");
        brand = brandRepository.save(brand);

        inventory = new Inventory();
        inventory.setBrand(brand);
        inventory.setQuantity(20);
        inventory.setReceivedTime(new Date());
        inventory = inventoryRepository.save(inventory);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindByBrandId() {
        List<Inventory> list = inventoryRepository.findByBrandId(brand.getId());
        Assert.assertFalse(list.isEmpty());
        Assert.assertTrue(list.stream().allMatch(i -> i.getBrandId().equals(brand.getId())));
    }

    /**
     * Update Scenario Test
     */
    @Test
    public void testUpdate() {
        Inventory result1 = inventoryRepository.getOne(inventory.getId());
        Assert.assertNotNull(result1);
        Assert.assertTrue(inventory.getBrand().getName().equals(result1.getBrand().getName()));
        Assert.assertTrue(inventory.getQuantity().equals(result1.getQuantity()));
        Assert.assertTrue(inventory.getReceivedTime().compareTo(result1.getReceivedTime()) == 0);

        // update existing entry
        result1.setQuantity(40);
        result1.setReceivedTime(new Date());
        result1 = inventoryRepository.save(result1);
        Inventory result2 = inventoryRepository.getOne(inventory.getId());
        Assert.assertNotNull(result2);
        Assert.assertTrue(result2.getBrandId().equals(inventory.getBrandId()));
        Assert.assertTrue(result2.getQuantity().equals(result1.getQuantity()));
    }

}
