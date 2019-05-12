package com.zappos.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zappos.backoffice.database.repository.InventoryRepository;
import com.zappos.backoffice.mapper.InventoryToTsvInventoryMapper;
import com.zappos.backoffice.tsv.domain.TsvInventory;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<TsvInventory> read(Long brandId) {
        List<TsvInventory> list = new ArrayList<>();
        InventoryToTsvInventoryMapper mapper = new InventoryToTsvInventoryMapper();
        if(null != brandId) {
            list.addAll(inventoryRepository.findByBrandId(brandId)
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .collect(Collectors.toList()));
        } else {
            list.addAll(inventoryRepository.findAll()
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .collect(Collectors.toList()));
        }
        return list;
    }
}
