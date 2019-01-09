package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_SCRAPE")
public class Scrape {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_SCRAPE_ID")
    private int id;

    @Column(name = "T_SCRAPE_TIME")
    private String time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
