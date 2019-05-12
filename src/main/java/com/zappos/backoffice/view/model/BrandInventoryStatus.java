package com.zappos.backoffice.view.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class BrandInventoryStatus implements Serializable {

    private static final long serialVersionUID = -8669611044876179165L;

    @Setter(value=AccessLevel.NONE)
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BrandInventory> data = new ArrayList<>();
}
