package com.zappos.backoffice.database.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name ="INVENTORY")
@IdClass(InventoryId.class)
@NoArgsConstructor
public class Inventory {

    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="BRAND_ID", nullable=false)
    private Brand brand;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Id
    @Column(name = "TIME_RECEIVED")
    private Date receivedTime;

    public Date getReceivedTime() {
        return receivedTime == null ? receivedTime : (Date)receivedTime.clone();
    }
    public void setReceivedTime(Date receivedTime) {
        if(null != receivedTime) {
            this.receivedTime = (Date)receivedTime.clone();
        }
    }
}
