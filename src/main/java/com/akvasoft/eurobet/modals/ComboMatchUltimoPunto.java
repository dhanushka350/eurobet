package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_COMBO_MATCH_ULTIMO_PUNTO")
public class ComboMatchUltimoPunto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_COMBO_MATCH_ULTIMO_PUNTO_ID")
    private int id;

    @Column(name = "T_COMBO_MATCH_ULTIMO_PUNTO_NAME")
    private String name;

    @Column(name = "T_COMBO_MATCH_ULTIMO_PUNTO_VALUE")
    private String value;

    @Column(name = "T_COMBO_MATCH_ULTIMO_PUNTO_SCRAPE_TIME")
    private String scrtime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATCH_ID")
    private Match match;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SCRAPE_ID")
    private Scrape scrape;

    public Scrape getScrape() {
        return scrape;
    }

    public void setScrape(Scrape scrape) {
        this.scrape = scrape;
    }

    public String getScrtime() {
        return scrtime;
    }

    public void setScrtime(String scrtime) {
        this.scrtime = scrtime;
    }

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

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
