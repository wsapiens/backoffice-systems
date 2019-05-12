package com.zappos.backoffice.view.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandInventory implements Serializable  {

    private static final long serialVersionUID = 5303572727394941850L;

    private String brandName;
    private int brandId;
    private int quantity;
}
