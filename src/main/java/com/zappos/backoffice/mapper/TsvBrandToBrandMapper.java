package com.zappos.backoffice.mapper;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.tsv.domain.TsvBrand;

/**
 * Mapper to transform TsvBrand to database Brand entity
 * this must be used within Transactional scope
 * @author spark
 *
 */
public class TsvBrandToBrandMapper implements DomainMapper<TsvBrand, Brand> {

    @Override
    public Brand map(TsvBrand source) {
        // probably better now pass the entity id. if we need, then better string type uuid style id
        return new Brand(Long.parseLong(Integer.toString(source.getId())), source.getName());
    }

}
