package com.zappos.backoffice.database.repository;

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

/**
 * This is testing BrandRepository by in-memory database
 * @author spark
 *
 */
@ContextConfiguration(classes = { RepositoryTestConfig.class })
@RunWith(SpringRunner.class)
@Transactional
@DirtiesContext
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository brandRepository;

    private Brand brand;

    @Before
    public void setUp() throws Exception {
        brand = new Brand();
        brand.setName("name");
        brand = brandRepository.save(brand);
    }

    @After
    public void tearDown() throws Exception {
        brandRepository.delete(brand);
    }

    /**
     * This is read test
     */
    @Test
    public void testGetOne() {
        Brand result = brandRepository.getOne(brand.getId());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getId() == brand.getId());
        Assert.assertTrue(brand.getName().equals(result.getName()));
    }

    @Test
    public void testFindByName() {
        Brand result = brandRepository.findByName(brand.getName());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getId() == brand.getId());
        Assert.assertTrue(brand.getName().equals(result.getName()));
    }

    /**
     * Update Scenario Test
     */
    @Test
    public void testUpdate() {
        Brand result1 = brandRepository.getOne(brand.getId());
        // update brand with different unique name
        result1.setName("differntName");
        brandRepository.save(result1);

        Brand result2 = brandRepository.getOne(brand.getId());
        Assert.assertTrue(result1.getName().equals(result2.getName()));
    }

}
