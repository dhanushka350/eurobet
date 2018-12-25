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
    private String name;

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
    private List<UOInclSuppl> uoInclSuppls = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOTotalPunti> uoTotalPuntis = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispariInchSuppl> pariDispariInchSuppls = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap1T> ttHandicap1TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<SenzaMargine_12_1T> senzaMargine_12_1TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UO1T> uo1TS = new ArrayList<>();


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispari1T> pariDispari1TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap2T> ttHandicap2TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<SenzaMargine_12_2T> senzaMargine_12_2TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UO2T> uo2TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispari2T> pariDispari2TS = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOQuarto_1> uoQuarto_1s = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOQuarto_2> uoQuarto_2s = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOQuarto_3> uoQuarto_3s = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOQuarto_4> uoQuarto_4s = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap_1Quarto> ttHandicap_1Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<SenzaMargine_12_1Quarto> senzaMargine_12_1Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispari1Quarto> pariDispari1Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap2Quarto> ttHandicap2Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<SenzaMargine_12_2Quarto> senzaMargine_12_2Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispari2Quarto> pariDispari2Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap3Quarto> ttHandicap3Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<SenzaMargine_12_3Quarto> senzaMargine_12_3Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispari3Quarto> pariDispari3Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTHandicap4Quarto> ttHandicap4Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<SenzaMargine_12_4Quarto> senzaMargine_12_4Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PariDispari4Quarto> pariDispari4Quartos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOCaseInclSuppl> uoCaseInclSuppls = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UOOspiteInclSuppl> uoOspiteInclSuppls = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TempoFinale_1> tempoFinale_1s = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<QuartoConPuntPiuAlto> quartoConPuntPiuAltos = new ArrayList<>();

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
