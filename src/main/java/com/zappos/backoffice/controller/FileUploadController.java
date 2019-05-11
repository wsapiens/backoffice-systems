package com.zappos.backoffice.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zappos.backoffice.error.BackOfficeException;
import com.zappos.backoffice.tsv.parser.TsvBrandParser;
import com.zappos.backoffice.tsv.parser.TsvInventoryParser;

/**
 * This is endpoint to trigger ETL task
 * For making it simple, all the endpoints expect local filepath 
 * this application can access, not a remote filepath
 * 
 * @author spark
 *
 */
@RestController(value = "/upload")
public class FileUploadController {
    static Logger log = Logger.getLogger(FileUploadController.class);

    @Autowired
    private TsvBrandParser tsvBrandParser;

    @Autowired
    private TsvInventoryParser tsvInventoryParser;

    /**
     * Upload TSV format Brand dump file
     * @param path local filepath which this application can access
     */
    @PostMapping(path = "/brands")
    public void uploadBrands(@RequestBody String path) {
        File file = new File(path);
        if(!file.exists() || !file.canRead()) {
            log.warn("can't read brand dump file");
            throw new BackOfficeException("can't read brand dump file");
        }
        tsvBrandParser.parse(file);
    }
}
