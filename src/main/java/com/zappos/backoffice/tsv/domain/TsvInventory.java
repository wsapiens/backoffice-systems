package com.zappos.backoffice.tsv.domain;

import lombok.Data;

/**
 * TSV domain object to hold parsed inventory entry from file
 * @author spark
 */
@Data
public class TsvInventory {
    private String brandId;
    private String quantity;
    private String receivedTime;
}
