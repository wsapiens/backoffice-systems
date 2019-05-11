package com.zappos.backoffice.tsv.domain;

import lombok.Data;

/**
 * TSV domain object to hold parsed brand entry from file
 * @author spark
 */
@Data
public class TsvBrand {
    private String id;
    private String name;
}
