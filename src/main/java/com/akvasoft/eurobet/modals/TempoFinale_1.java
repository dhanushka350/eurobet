package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_TEMPO_FINALE_1")
public class TempoFinale_1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_TEMPO_FINALE_1_ID")
    private int id;

    @Column(name = "T_TEMPO_FINALE_1_NAME")
    private String name;

    @Column(name = "T_TEMPO_FINALE_1_VALUE")
    private String value;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_TEMPO_FINALE_1_MATCH")
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
