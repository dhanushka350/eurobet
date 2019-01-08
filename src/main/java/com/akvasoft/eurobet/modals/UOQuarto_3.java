package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_UO_3_QUARTO")
public class UOQuarto_3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_UO_3_QUARTO_ID")
    private int id;

    @Column(name = "T_UO_3_QUARTO_NAME")
    private String name;

    @Column(name = "T_UO_3_QUARTO_UNDER")
    private String under;

    @Column(name = "T_UO_3_QUARTO_OVER")
    private String over;

    @Column(name = "T_UO_3_QUARTO_SCRAPE_TIME")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnder() {
        return under;
    }

    public void setUnder(String under) {
        this.under = under;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
