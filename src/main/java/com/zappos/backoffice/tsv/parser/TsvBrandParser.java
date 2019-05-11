package com.zappos.backoffice.tsv.parser;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.zappos.backoffice.error.BackOfficeException;
import com.zappos.backoffice.tsv.domain.TsvBrand;

@Component
public class TsvBrandParser implements TsvParser<TsvBrand> {
    static Logger log = Logger.getLogger(TsvBrandParser.class);

    @Override
    public List<TsvBrand> parse(File file) {
        List<TsvBrand> list = new ArrayList<>();
        try(RandomAccessFile rafile = new RandomAccessFile(file, "r")) {
            int idIndex = -1;
            int nameIndex = -1;
            String line;
            while((line = rafile.readLine()) !=  null && StringUtils.isNotBlank(line)) {
                String[] columns = StringUtils.split(line, '\t');
                // find column order from header
                if(-1 == idIndex || -1 == nameIndex ) {
                    for(int i = 0; i < columns.length; i++) {
                        String label = StringUtils.strip(columns[i], "\"");
                        log.debug("column label: ".concat(label));
                        if(StringUtils.equalsIgnoreCase(label, "BRAND_ID")) {
                            idIndex = i;
                        } else if(StringUtils.equalsIgnoreCase(label, "Name")) {
                            nameIndex = i;
                        }
                    }
                    if(-1 == idIndex || -1 == nameIndex ) {
                        throw new BackOfficeException("fail to find column order");
                    }
                } else {
                    TsvBrand brand = new TsvBrand();
                    brand.setId(Integer.parseInt(columns[idIndex]));
                    brand.setName(columns[nameIndex]);
                    list.add(brand);
                }
            }
        } catch(NumberFormatException | IOException e) {
            log.error(e.getMessage());
            throw new BackOfficeException(e);
        }
        return list;
    }

}
