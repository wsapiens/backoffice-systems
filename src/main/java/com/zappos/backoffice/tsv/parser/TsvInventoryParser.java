package com.zappos.backoffice.tsv.parser;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zappos.backoffice.error.BackOfficeException;
import com.zappos.backoffice.tsv.domain.TsvInventory;

public class TsvInventoryParser implements TsvParser<TsvInventory> {
    static Logger log = Logger.getLogger(TsvInventoryParser.class);

    @Override
    public List<TsvInventory> parse(File file) {
        List<TsvInventory> list = new ArrayList<>();
        try(RandomAccessFile rafile = new RandomAccessFile(file, "r")) {
            int idIndex = -1;
            int quantityIndex = -1;
            int timeIndex = -1;
            String line;
            while((line = rafile.readLine()) !=  null && StringUtils.isNotBlank(line)) {
                String[] columns = StringUtils.split(line, '\t');
                // find column order from header
                if(-1 == idIndex || -1 == quantityIndex || -1 == timeIndex) {
                    for(int i = 0; i < columns.length; i++) {
                        String label = StringUtils.strip(columns[i], "\"");
                        log.debug("column label: ".concat(label));
                        if(StringUtils.equalsIgnoreCase(label, "TIME_RECEIVED")) {
                            timeIndex = i;
                        } else if(StringUtils.equalsIgnoreCase(label, "QUANTITY")) {
                            quantityIndex = i;
                        } else if(StringUtils.equalsIgnoreCase(label, "BRAND_ID")) {
                            idIndex = i;
                        }
                    }
                    if(-1 == idIndex || -1 == quantityIndex || -1 == timeIndex ) {
                        throw new BackOfficeException("fail to find column order");
                    }
                } else {
                    TsvInventory inventory = new TsvInventory();
                    inventory.setBrandId(Integer.parseInt(columns[idIndex]));
                    inventory.setQuantity(Integer.parseInt(columns[quantityIndex]));
                    inventory.setReceivedTime(columns[timeIndex]);
                    list.add(inventory);
                }
            }
        } catch(Exception e) {
            log.error(e.getMessage());
            throw new BackOfficeException(e);
        }
        return list;
    }

}
