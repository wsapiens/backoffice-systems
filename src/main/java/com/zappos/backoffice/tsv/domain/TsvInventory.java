package com.zappos.backoffice.tsv.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TSV domain object to hold parsed inventory entry from file
 * @author spark
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsvInventory {
    private int brandId;
    private int quantity;
    private String receivedTime;
}
