package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_12_4QUARTO_SENZA_MARGINE")
public class SenzaMargine_12_4Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_12_4QUARTO_SENZA_MARGINE_ID")
    private int id;

    @Column(name = "T_12_4QUARTO_SENZA_MARGINE_ONE")
    private String one;

    @Column(name = "T_12_4QUARTO_SENZA_MARGINE_MULTIPLE")
    private String multi;

    @Column(name = "T_12_4QUARTO_SENZA_MARGINE_TWO")
    private String two;

    @Column(name = "T_12_4QUARTO_SENZA_MARGINE_SCRAPE_TIME")
    private String scrtime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATCH_ID")
    private Match match;

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

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getMulti() {
        return multi;
    }

    public void setMulti(String multi) {
        this.multi = multi;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
