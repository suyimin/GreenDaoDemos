package com.xdroid.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Order {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "date")
    private String date;

    @Property(nameInDb = "customId")
    private Long customId;

    @Generated(hash = 915161134)
    public Order(Long id, String date, Long customId) {
        this.id = id;
        this.date = date;
        this.customId = customId;
    }

    @Generated(hash = 1105174599)
    public Order() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getCustomId() {
        return this.customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

}
