package com.zappos.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.database.repository.BrandRepository;
import com.zappos.backoffice.database.repository.InventoryRepository;
import com.zappos.backoffice.mapper.InventoryToTsvInventoryMapper;
import com.zappos.backoffice.mapper.TsvInventoryToInventoryMapper;
import com.zappos.backoffice.tsv.domain.TsvInventory;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<TsvInventory> read(Long brandId) {
        List<TsvInventory> list = new ArrayList<>();
        InventoryToTsvInventoryMapper mapper = new InventoryToTsvInventoryMapper();
        if(null != brandId) {
            list.addAll(inventoryRepository.findByBrandId(brandId)
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList()));
        } else {
            list.addAll(inventoryRepository.findAll()
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList()));
        }
        return list;
    }

    public void save(List<TsvInventory> tsvInventories) {
        TsvInventoryToInventoryMapper mapper = new TsvInventoryToInventoryMapper();
        List<Brand> brands = new ArrayList<>();
        List<Inventory> inventories = tsvInventories.stream()
                                                    .map(s -> {
                                                        Inventory inv = mapper.map(s);
                                                        Long brandId = Long.valueOf(Integer.toString(s.getBrandId()));
                                                        Brand brand = brandRepository.getOne(brandId);
                                                        inv.setBrand(brand);
                                                        brands.add(brand);
                                                        return inv;
                                                    })
                                                    .collect(Collectors.toList());
        inventoryRepository.saveAll(inventories);
    }
}
