package com.akvasoft.eurobet.modals;

import javax.persistence.*;

@Entity
@Table(name = "T_SCRAPE_LINKS")
public class ScrapeLinks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_SCRAPE_LINKS_ID")
    private int id;

    @Column(name = "T_SCRAPE_LINKS_LEAGUE")
    private String name;

    @Column(name = "T_SCRAPE_LINKS_LINK")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
