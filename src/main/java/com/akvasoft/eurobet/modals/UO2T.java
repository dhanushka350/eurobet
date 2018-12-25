package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_UO_2T")
public class UO2T {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_UO_2T_ID")
    private int id;

    @Column(name = "T_UO_2T_NAME")
    private String name;

    @Column(name = "T_UO_2T_UNDER")
    private String under;

    @Column(name = "T_UO_2T_OVER")
    private String over;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_UO_2T_MATCH")
    private Match match;

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
