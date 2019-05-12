package com.zappos.backoffice.mapper;

import com.zappos.backoffice.database.model.Brand;
import com.zappos.backoffice.tsv.domain.TsvBrand;

/**
 * Mapper to transform database domain Brand to TsvBrand for restul communication
 * Brand is database entity, so please use this within Transactional scope
 * 
 * @author spark
 *
 */
public class BrandToTsvBrandMapper implements DomainMapper<Brand, TsvBrand> {

    @Override
    public TsvBrand map(Brand source) {
        if(null == source || null == source.getId()) {
            return null;
        }
        // better now pass the entity id. if we need, then better string type uuid style id
        return new TsvBrand(source.getId().intValue(), source.getName());
    }

}
