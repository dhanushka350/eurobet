package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_SUPPLEMENTARI")
public class Supplementari {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_SUPPLEMENTARI_ID")
    private int id;

    @Column(name = "T_SUPPLEMENTARI_SL")
    private String sl;

    @Column(name = "T_SUPPLEMENTARI_NO")
    private String no;

    @Column(name = "T_SUPPLEMENTARI_SCRAPE_TIME")
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

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
