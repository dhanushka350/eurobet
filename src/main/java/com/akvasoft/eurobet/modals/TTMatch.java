package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_T_MATCH")
public class TTMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_MATCH_ID")
    private int ttmid;

    @Column(name = "T_MATCH_ONE")
    private String one;

    @Column(name = "T_MATCH_TWO")
    private String two;

    @Column(name = "T_MATCH_SCRAPE_TIME")
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
    public int getTtmid() {
        return ttmid;
    }

    public String getScrtime() {
        return scrtime;
    }

    public void setScrtime(String scrtime) {
        this.scrtime = scrtime;
    }

    public void setTtmid(int ttmid) {
        this.ttmid = ttmid;
    }

    public int getId() {
        return ttmid;
    }

    public void setId(int id) {
        this.ttmid = id;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
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
