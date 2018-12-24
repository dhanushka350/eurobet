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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "T_SUPPLEMENTARI_MATCH")
    private Match match;

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
