package com.zappos.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.database.repository.BrandRepository;
import com.zappos.backoffice.database.repository.InventoryRepository;
import com.zappos.backoffice.mapper.BrandToTsvBrandMapper;
import com.zappos.backoffice.mapper.TsvBrandToBrandMapper;
import com.zappos.backoffice.tsv.domain.TsvBrand;

/**
 * CRUD Service for Brand
 */
@Service
@Transactional
public class BrandService {
    static Logger log = Logger.getLogger(BrandService.class);

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Read database Brand entries and convert to list of TsvBrand domain objects
     * @param id Brand id and if id is given, return only one in the list
     * @param name Brand name and if name is given, return only one in the list
     * @param pageable for paging
     * @return list of TsvBrand objects
     */
    public List<TsvBrand> read(Long id, String name, Pageable pageable) {
        List<TsvBrand> list = new ArrayList<>();
        BrandToTsvBrandMapper mapper = new BrandToTsvBrandMapper();
        if(null != id) {
            list.add(mapper.map(brandRepository.findById(id).orElse(null)));
        } else if (null != name) {
            list.add(mapper.map(brandRepository.findByName(name)));
        } else {
            if(null != pageable) {
                list.addAll(brandRepository.findAll(pageable)
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList()));
            } else {
                list.addAll(brandRepository.findAll()
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .filter(Objects::nonNull)
                                            .collect(Collectors.toList()));
            }
        }
        return list.stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
    }

    /**
     * Save single database Brand entry by tsvBrand.
     * We assume id is same, but only name is changed, otherwise this will add new one.
     * @param tsvBrand input object
     */
    public void save(TsvBrand tsvBrand) {
        if(null != tsvBrand) {
            TsvBrandToBrandMapper mapper = new TsvBrandToBrandMapper();
            brandRepository.save(mapper.map(tsvBrand));
        }
    }

    /**
     * Save multiple Brand entries by list of tsvBrand objects
     * @param tsvBrands list of tsvBrand objects
     * @return list of saved tsvBrand objects
     */
    public List<TsvBrand> save(List<TsvBrand> tsvBrands) {
        List<Brand> brands = tsvBrands.stream()
                                    .map(t -> new TsvBrandToBrandMapper().map(t))
                                    .collect(Collectors.toList());
        List<Brand> results = brandRepository.saveAll(brands);
        return results.stream()
                    .map(r -> new BrandToTsvBrandMapper().map(r))
                    .collect(Collectors.toList());
    }

    /**
     * Update multiple Brands on database by given id
     * if don't find brand by id, that will be filtered out.
     * This is for not creating a deleted entry by update unknowingly
     * @param tsvBrands list of TsvBrand domain objects contain the brand id
     * @return list of saved tsvBrand objects
     */
    public List<TsvBrand> update(List<TsvBrand> tsvBrands) {
        List<Brand> brands = tsvBrands.stream()
                                    .filter(b -> {
                                        Optional<Brand> opt = brandRepository.findById(Long.valueOf(Integer.toString(b.getId())));
                                        return opt.isPresent();
                                    })
                                    .map(t -> new TsvBrandToBrandMapper().map(t))
                                    .collect(Collectors.toList());
        List<Brand> results = brandRepository.saveAll(brands);
        return results.stream()
                    .map(r -> new BrandToTsvBrandMapper().map(r))
                    .collect(Collectors.toList());
    }

    /**
     * Detele single database Brand entiry by id or name
     * @param id if id is given, delete by id
     * @param name if name is given, delete by name
     */
    public void delete(Long id, String name) {
        Brand brand = null;
        if(null != id) {
            brand = brandRepository.getOne(id);
        } else if (null != name) {
            brand = brandRepository.findByName(name);
        }
        if(null != brand) {
            List<Inventory> inventories = inventoryRepository.findByBrandId(brand.getId());
            // delete inventories first
            inventoryRepository.deleteAll(inventories);
            brandRepository.delete(brand);
        }
    }

    /**
     * Delete multiple Brand entries
     * @param tsvBrands list of TsvBrand objects to delete
     */
    public void delete(List<TsvBrand> tsvBrands) {
        List<Brand> brands = new ArrayList<>();
        List<Inventory> inventories = new ArrayList<>();
        tsvBrands.forEach(t -> {
            Optional<Brand> opt = brandRepository.findById(Long.valueOf(Integer.toString(t.getId())));
            opt.ifPresent(brand -> {
                log.info("delete brand id: ".concat(brand.getId().toString()));
                brands.add(brand);
                inventories.addAll(inventoryRepository.findByBrandId(brand.getId()));
            });
        });
        // delete inventories first
        inventoryRepository.deleteAll(inventories);
        brandRepository.deleteAll(brands);
    }
}
