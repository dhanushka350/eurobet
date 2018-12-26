package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_PRIMA_SQUADRA_SEGNARE")
public class PrimaSquadraSegnare {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_PRIMA_SQUADRA_SEGNARE_ID")
    private int id;

    @Column(name = "T_PRIMA_SQUADRA_SEGNARE_TEAM1")
    private String team1;

    @Column(name = "T_PRIMA_SQUADRA_SEGNARE_TEAM2")
    private String team2;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_PRIMA_SQUADRA_SEGNARE_MATCH")
    private Match match;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}