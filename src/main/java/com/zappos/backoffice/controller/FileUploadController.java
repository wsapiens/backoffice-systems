package com.zappos.backoffice.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zappos.backoffice.service.BrandService;
import com.zappos.backoffice.service.InventoryService;
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
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {
    static Logger log = Logger.getLogger(FileUploadController.class);

    @Autowired
    private TsvBrandParser tsvBrandParser;

    @Autowired
    private TsvInventoryParser tsvInventoryParser;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    /**
     * Upload TSV format Brand dump file
     * @param file query param for multipartfile 
     * @return string type result
     */
    @PostMapping("/brands")
    public @ResponseBody String uploadBrands(@RequestParam("file") MultipartFile file) {
        log.debug("received brand file uploading request");
        try {
            brandService.save(tsvBrandParser.parse(saveMultipartFile(file)));
        } catch (IOException e) {
            log.error("fail to find current path");
            return "brand upload failed!!! ";
        }
        return "brands uploaded successfully... ";
    }

    @PostMapping("/inventories")
    public @ResponseBody String uploadInventories(@RequestParam("file") MultipartFile file) {
        log.debug("received brand file uploading request");
        try {
            inventoryService.save(tsvInventoryParser.parse(saveMultipartFile(file)));
        } catch (IOException e) {
            log.error("fail to find current path");
            return "inventory upload failed!!! ";
        }
        return "inventories uploaded successfully... ";
    }

    private File saveMultipartFile(MultipartFile multipartFile) throws IOException {
        String path = new File(".").getCanonicalPath();
        Path filepath = Paths.get(path, multipartFile.getOriginalFilename());

        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(multipartFile.getBytes());
        }
        return new File(filepath.toString());
    }
}
