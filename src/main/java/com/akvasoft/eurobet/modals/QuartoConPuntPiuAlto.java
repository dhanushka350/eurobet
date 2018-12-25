package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_QUARTO_CON_PUNT_PIU_ALTO")
public class QuartoConPuntPiuAlto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_QUARTO_CON_PUNT_PIU_ALTO_ID")
    private int id;

    @Column(name = "T_QUARTO_CON_PUNT_PIU_ALTO_NAME")
    private String name;

    @Column(name = "T_QUARTO_CON_PUNT_PIU_ALTO_VALUE")
    private String value;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_QUARTO_CON_PUNT_PIU_ALTO_MATCH")
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
