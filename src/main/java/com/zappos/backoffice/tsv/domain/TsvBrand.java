package com.zappos.backoffice.tsv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TSV domain object to hold parsed brand entry from file
 * @author spark
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsvBrand {
    private int id;
    private String name;
}
