package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_TEMPI_REGOLAM")
public class TempiRegolam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_TEMPI_REGOLAM_ID")
    private int id;

    @Column(name = "T_TEMPI_REGOLAM_ONE")
    private String one;

    @Column(name = "T_TEMPI_REGOLAM_MULTIPLE")
    private String multiple;

    @Column(name = "T_TEMPI_REGOLAM_TWO")
    private String two;

    @Column(name = "T_TEMPI_REGOLAM_SCRAPE_TIME")
    private String scrtime;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_TEMPI_REGOLAM_MATCH")
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

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
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
