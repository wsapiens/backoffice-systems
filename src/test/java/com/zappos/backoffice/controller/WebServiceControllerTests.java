package com.zappos.backoffice.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zappos.backoffice.service.ServiceTestConfig;
import com.zappos.backoffice.tsv.domain.TsvBrand;
import com.zappos.backoffice.view.model.BrandStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockServletContext.class, ServiceTestConfig.class, ControllerTestConfig.class})
@WebAppConfiguration
public class WebServiceControllerTests {

    @Autowired
    private WebServiceController webServiceController;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(webServiceController).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetBrands() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/service/v1/brands"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andReturn();
    }

    @Test
    public void testCreateBrands() throws Exception {
        BrandStatus status = new BrandStatus();
        status.getBrands().add(new TsvBrand(1, "name"));
        Gson gson = new GsonBuilder().serializeNulls().create();
        String payload = gson.toJson(status);
        mvc.perform(MockMvcRequestBuilders.post("/service/v1/brands")
                                          .contentType("application/json; charset=utf-8")
                                          .content(payload))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andReturn();
    }

    @Test
    public void testUpdateBrands() throws Exception {
        BrandStatus status = new BrandStatus();
        status.getBrands().add(new TsvBrand(1, "name"));
        Gson gson = new GsonBuilder().serializeNulls().create();
        String payload = gson.toJson(status);
        mvc.perform(MockMvcRequestBuilders.put("/service/v1/brands")
                                          .contentType("application/json; charset=utf-8")
                                          .content(payload))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andReturn();
    }

    @Test
    public void testDeleteBrands() throws Exception {
        BrandStatus status = new BrandStatus();
        status.getBrands().add(new TsvBrand(1, "name"));
        Gson gson = new GsonBuilder().serializeNulls().create();
        String payload = gson.toJson(status);
        mvc.perform(MockMvcRequestBuilders.delete("/service/v1/brands")
                                          .contentType("application/json; charset=utf-8")
                                          .content(payload))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andReturn();
    }

    @Test
    public void testGetBrandsInventories() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/service/v1/brands/inventories"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andReturn();
    }

}
