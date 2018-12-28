package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_T_UO_INCL_SUPPL")
public class TTUOInclSuppl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_UO_INCL_SUPPL_ID")
    private int id;

    @Column(name = "T_UO_INCL_SUPPL_NAME")
    private String name;

    @Column(name = "T_UO_INCL_SUPPL_TEAM_ONE_UNDER")
    private String teamOneUnder;

    @Column(name = "T_UO_INCL_SUPPL_TEAM_TWO_UNDER")
    private String teamTwoUnder;

    @Column(name = "T_UO_INCL_SUPPL_TEAM_ONE_OVER")
    private String teamOneOver;

    @Column(name = "T_UO_INCL_SUPPL_TEAM_TWO_OVER")
    private String teamTwoOver;

    @Column(name = "T_UO_INCL_SUPPL_SCRAPE_TIME")
    private String scrtime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_UO_INCL_SUPPL_MATCH")
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

    public String getTeamOneUnder() {
        return teamOneUnder;
    }

    public void setTeamOneUnder(String teamOneUnder) {
        this.teamOneUnder = teamOneUnder;
    }

    public String getTeamTwoUnder() {
        return teamTwoUnder;
    }

    public void setTeamTwoUnder(String teamTwoUnder) {
        this.teamTwoUnder = teamTwoUnder;
    }

    public String getTeamOneOver() {
        return teamOneOver;
    }

    public void setTeamOneOver(String teamOneOver) {
        this.teamOneOver = teamOneOver;
    }

    public String getTeamTwoOver() {
        return teamTwoOver;
    }

    public void setTeamTwoOver(String teamTwoOver) {
        this.teamTwoOver = teamTwoOver;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
