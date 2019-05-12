package com.zappos.backoffice.view.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.zappos.backoffice.tsv.domain.TsvBrand;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class BrandStatus {
    @Setter(value=AccessLevel.NONE)
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TsvBrand> brands = new ArrayList<>();
}
