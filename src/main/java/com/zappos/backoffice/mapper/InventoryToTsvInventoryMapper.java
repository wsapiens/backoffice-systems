package com.zappos.backoffice.mapper;

import static com.zappos.backoffice.util.DateTimeUtils.formatIsoUtcDateTime;

import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.tsv.domain.TsvInventory;

/**
 * Convert database Inventory to TsvInventory
 * @author spark
 *
 */
public class InventoryToTsvInventoryMapper implements DomainMapper<Inventory, TsvInventory>{

    @Override
    public TsvInventory map(Inventory source) {
        if(null == source || null == source.getBrandId()) {
            return null;
        }
        return new TsvInventory(source.getBrandId().intValue(), source.getQuantity(), formatIsoUtcDateTime(source.getReceivedTime()));
    }

}
