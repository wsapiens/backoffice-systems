package com.zappos.backoffice.database.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name ="BRAND")
public class Brand {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name="NAME")
    private String name;

    @OneToMany(cascade={CascadeType.ALL}, mappedBy="brand", fetch=FetchType.LAZY)
    private Set<Inventory> inventories = new HashSet<>();
}
