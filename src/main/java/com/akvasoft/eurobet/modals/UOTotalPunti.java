package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_UO_TOTAL_PUNTI")
public class UOTotalPunti {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_UO_TOTAL_PUNTI_ID")
    private int id;

    @Column(name = "T_UO_TOTAL_PUNTI_NAME")
    private String name;

    @Column(name = "UO_TOTAL_PUNTI_UNDER")
    private String under;

    @Column(name = "UO_TOTAL_PUNTI_OVER")
    private String over;

    @Column(name = "UO_TOTAL_PUNTI_ESATTAMENTE")
    private String esattamente;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UO_TOTAL_PUNTI_MATCH")
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

    public String getEsattamente() {
        return esattamente;
    }

    public void setEsattamente(String esattamente) {
        this.esattamente = esattamente;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}