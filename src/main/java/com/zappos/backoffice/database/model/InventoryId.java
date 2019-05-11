package com.zappos.backoffice.database.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryId implements Serializable {

    private static final long serialVersionUID = 2994849423913009031L;

    private Brand brand;
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
