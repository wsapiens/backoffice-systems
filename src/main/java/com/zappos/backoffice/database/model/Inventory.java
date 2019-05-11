package com.zappos.backoffice.database.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name ="INVENTORY")
@NoArgsConstructor
public class Inventory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="BRAND_ID")
    private Long brandId;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="BRAND_ID", referencedColumnName="ID", insertable=false, updatable=false)
    private Brand brand;

    @Column(name = "QUANTITY", nullable=false)
    private Integer quantity;

    @Column(name = "TIME_RECEIVED", nullable=false)
    private Date receivedTime;

    public void setBrand(Brand brand) {
        this.brand = brand;
        if(null != brand) {
            this.brandId = brand.getId();
        }
    }

    public Date getReceivedTime() {
        return receivedTime == null ? receivedTime : (Date)receivedTime.clone();
    }
    public void setReceivedTime(Date receivedTime) {
        if(null != receivedTime) {
            this.receivedTime = (Date)receivedTime.clone();
        }
    }
}
