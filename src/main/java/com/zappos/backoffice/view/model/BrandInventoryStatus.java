package com.zappos.backoffice.view.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class BrandInventoryStatus {
    @Setter(value=AccessLevel.NONE)
    List<BrandInventory> brands = new ArrayList<>();
}
