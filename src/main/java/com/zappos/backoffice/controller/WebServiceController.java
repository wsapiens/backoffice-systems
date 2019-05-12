package com.zappos.backoffice.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zappos.backoffice.service.BrandService;
import com.zappos.backoffice.service.InventoryService;
import com.zappos.backoffice.tsv.domain.TsvInventory;
import com.zappos.backoffice.view.model.BrandInventory;
import com.zappos.backoffice.view.model.BrandInventoryStatus;
import com.zappos.backoffice.view.model.BrandStatus;

@RestController
@RequestMapping(value = "/service/v1")  // service version 1
public class WebServiceController {
    Logger log = Logger.getLogger(WebServiceController.class);

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(path = "/brands",
                produces = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE })
    public BrandStatus readBrands(@RequestParam(required = false) Long id,
                                  @RequestParam(required = false) String name) {
        log.info("read brands");
        BrandStatus status = new BrandStatus();
        status.getBrands().addAll(brandService.read(id, name, null));
        return status;
    }

    @PostMapping(path = "/brands",
                consumes = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE })
    public BrandStatus createBrands(@RequestBody BrandStatus brandStatus) {
        log.info("create brands");
        BrandStatus status = new BrandStatus();
        status.getBrands().addAll(brandService.save(brandStatus.getBrands()));
        return status;
    }

    @PutMapping(path = "/brands",
                consumes = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE })
    public BrandStatus updateBrands(@RequestBody BrandStatus brandStatus) {
        log.info("update brands");
        BrandStatus status = new BrandStatus();
        status.getBrands().addAll(brandService.update(brandStatus.getBrands()));
        return status;
    }

    @DeleteMapping(path = "/brands",
                   consumes = { MediaType.APPLICATION_JSON_VALUE,
                                MediaType.APPLICATION_XML_VALUE })
    public void deleteBrands(@RequestBody BrandStatus brandStatus) {
        log.info("delete brands");
        brandService.delete(brandStatus.getBrands());
    }

    @GetMapping(path = "/brands/inventories",
                produces = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.APPLICATION_XML_VALUE })
    public BrandInventoryStatus readBrandsInventories() {
    log.info("read brands");
    BrandInventoryStatus status = new BrandInventoryStatus();
    brandService.read(null, null, null)
                .forEach(b -> {
                    List<TsvInventory> invs = inventoryService.read(Long.valueOf(Integer.toString(b.getId())));
                    status.getBrands().add(new BrandInventory(b.getName(),
                                                            b.getId(),
                                                            invs.stream().mapToInt(TsvInventory::getQuantity).sum()));
                });
        return status;
    }
}
