package com.zappos.backoffice.mapper;

import static com.zappos.backoffice.util.DateTimeUtils.parseIsoUtcString;

import java.text.ParseException;

import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.error.BackOfficeException;
import com.zappos.backoffice.tsv.domain.TsvInventory;

public class TsvInventoryToInventoryMapper implements DomainMapper<TsvInventory, Inventory>{

    @Override
    public Inventory map(TsvInventory source) {
        Inventory inventory = new Inventory();
        try {
            inventory.setBrandId(Long.valueOf(Integer.toString(source.getBrandId())));
            inventory.setQuantity(source.getQuantity());
            inventory.setReceivedTime(parseIsoUtcString(source.getReceivedTime()));
        } catch(ParseException e) {
            throw new BackOfficeException(e);
        }
        return inventory;
    }

}
