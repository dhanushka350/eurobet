package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_T_HANDICAP_2QUARTO")
public class TTHandicap2Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_HANDICAP_2QUARTO_ID")
    private int id;

    @Column(name = "T_HANDICAP_2QUARTO_NAME")
    private String name;

    @Column(name = "T_HANDICAP_2QUARTO_ONE")
    private String one;

    @Column(name = "T_HANDICAP_2QUARTO_TWO")
    private String two;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_HANDICAP_2QUARTO_MATCH")
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
