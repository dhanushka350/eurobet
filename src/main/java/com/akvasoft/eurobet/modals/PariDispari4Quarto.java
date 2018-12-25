package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_PARI_DISPARI_4QUARTO")
public class PariDispari4Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_PARI_DISPARI_4QUARTO_ID")
    private int id;

    @Column(name = "T_PARI_DISPARI_4QUARTO_PARI")
    private String pari;

    @Column(name = "T_PARI_DISPARI_4QUARTO_DISPARI")
    private String dispari;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_PARI_DISPARI_4QUARTO_MATCH")
    private Match match;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPari() {
        return pari;
    }

    public void setPari(String pari) {
        this.pari = pari;
    }

    public String getDispari() {
        return dispari;
    }

    public void setDispari(String dispari) {
        this.dispari = dispari;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
