package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "T_UO_OSPITE_INCL_SUPPL")
public class UOOspiteInclSuppl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UO_OSPITE_INCL_SUPPL_ID")
    private int id;

    @Column(name = "UO_OSPITE_INCL_SUPPL_NAME")
    private String name;

    @Column(name = "UO_OSPITE_INCL_SUPPL_UNDER")
    private String under;

    @Column(name = "UO_OSPITE_INCL_SUPPL_OVER")
    private String over;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UO_OSPITE_INCL_SUPPL_MATCH")
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

    public String getUnder() {
        return under;
    }

    public void setUnder(String under) {
        this.under = under;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
