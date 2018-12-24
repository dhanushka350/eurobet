package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_MATCH")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_MATCH_ID")
    private int id;

    @Column(name = "T_MATCH_NAME")
    private String Name;

    @Column(name = "T_MATCH_DATE")
    private String date;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTMatch> ttMatches = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap> ttHandicaps = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TPTI55> pti55s = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TempiRegolam> tempiRegolams = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<Supplementari> supplementaris = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTUOInclSuppl> ttuoInclSuppls = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOInclSuppl>  uoInclSuppls = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
