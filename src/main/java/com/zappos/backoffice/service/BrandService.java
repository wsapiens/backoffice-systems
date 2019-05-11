package com.zappos.backoffice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.database.repository.BrandRepository;
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
            list.add(mapper.map(brandRepository.getOne(id)));
        } else if (null != name) {
            list.add(mapper.map(brandRepository.findByName(name)));
        } else {
            if(null != pageable) {
                list.addAll(brandRepository.findAll(pageable)
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .collect(Collectors.toList()));
            } else {
                list.addAll(brandRepository.findAll()
                                            .stream()
                                            .map(s -> mapper.map(s))
                                            .collect(Collectors.toList()));
            }
        }
        return list;
    }

    /**
     * Update or Create single database Brand entry by tsvBrand.
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
     * Update or Create multiple Brand entries by list of tsvBrand objects
     * @param tsvBrands list of tsvBrand objects
     */
    public void save(List<TsvBrand> tsvBrands) {
        List<Brand> brands = tsvBrands.stream()
                                    .map(t -> new TsvBrandToBrandMapper().map(t))
                                    .collect(Collectors.toList());
        brandRepository.saveAll(brands);

    }

    /**
     * Detele single database Brand entiry by id or name
     * @param id if id is given, delete by id
     * @param name if name is given, delete by name
     */
    public void delete(Long id, String name) {
        if(null != id) {
            brandRepository.deleteById(id);
        } else if (null != name) {
            Brand brand = brandRepository.findByName(name);
            if(null != brand) {
                brandRepository.delete(brand);
            }
        }
    }

    /**
     * Delete multiple Brand entries
     * @param tsvBrands list of TsvBrand objects to delete
     */
    public void delete(List<TsvBrand> tsvBrands) {
        List<Brand> brands = tsvBrands.stream()
                                    .map(t -> new TsvBrandToBrandMapper().map(t))
                                    .collect(Collectors.toList());
        brandRepository.deleteAll(brands);
    }
}
