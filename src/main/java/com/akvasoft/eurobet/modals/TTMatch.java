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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_MATCH")
    private Match match;


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
