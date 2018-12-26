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

    @Column(name = "T_MATCH_TEAM_ONE")
    private String one;

    @Column(name = "T_MATCH_TEAM_TWO")
    private String two;

    @Column(name = "T_MATCH_DATE")
    private String date;

    @Column(name = "T_MATCH_TIME")
    private String time;

    @Column(name = "T_MATCH_STATUS")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<UltimaSquadraSegnare> ultimaSquadraSegnares = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<ComboMatchUltimoPunto> comboMatchUltimoPuntos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<TTMatch> ttMatches = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "match", fetch = FetchType.LAZY)
    private List<PrimaSquadraSegnare> primaSquadraSegnares = new ArrayList<>();

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<TTMatch> getTtMatches() {
        return ttMatches;
    }

    public void setTtMatches(List<TTMatch> ttMatches) {
        this.ttMatches = ttMatches;
    }

    public List<TTHandicap> getTtHandicaps() {
        return ttHandicaps;
    }

    public void setTtHandicaps(List<TTHandicap> ttHandicaps) {
        this.ttHandicaps = ttHandicaps;
    }

    public List<TPTI55> getPti55s() {
        return pti55s;
    }

    public void setPti55s(List<TPTI55> pti55s) {
        this.pti55s = pti55s;
    }

    public List<TempiRegolam> getTempiRegolams() {
        return tempiRegolams;
    }

    public void setTempiRegolams(List<TempiRegolam> tempiRegolams) {
        this.tempiRegolams = tempiRegolams;
    }

    public List<Supplementari> getSupplementaris() {
        return supplementaris;
    }

    public void setSupplementaris(List<Supplementari> supplementaris) {
        this.supplementaris = supplementaris;
    }

    public List<TTUOInclSuppl> getTtuoInclSuppls() {
        return ttuoInclSuppls;
    }

    public void setTtuoInclSuppls(List<TTUOInclSuppl> ttuoInclSuppls) {
        this.ttuoInclSuppls = ttuoInclSuppls;
    }

    public List<UOInclSuppl> getUoInclSuppls() {
        return uoInclSuppls;
    }

    public void setUoInclSuppls(List<UOInclSuppl> uoInclSuppls) {
        this.uoInclSuppls = uoInclSuppls;
    }

    public List<UOTotalPunti> getUoTotalPuntis() {
        return uoTotalPuntis;
    }

    public void setUoTotalPuntis(List<UOTotalPunti> uoTotalPuntis) {
        this.uoTotalPuntis = uoTotalPuntis;
    }

    public List<PariDispariInchSuppl> getPariDispariInchSuppls() {
        return pariDispariInchSuppls;
    }

    public void setPariDispariInchSuppls(List<PariDispariInchSuppl> pariDispariInchSuppls) {
        this.pariDispariInchSuppls = pariDispariInchSuppls;
    }

    public List<TTHandicap1T> getTtHandicap1TS() {
        return ttHandicap1TS;
    }

    public void setTtHandicap1TS(List<TTHandicap1T> ttHandicap1TS) {
        this.ttHandicap1TS = ttHandicap1TS;
    }

    public List<SenzaMargine_12_1T> getSenzaMargine_12_1TS() {
        return senzaMargine_12_1TS;
    }

    public void setSenzaMargine_12_1TS(List<SenzaMargine_12_1T> senzaMargine_12_1TS) {
        this.senzaMargine_12_1TS = senzaMargine_12_1TS;
    }

    public List<UO1T> getUo1TS() {
        return uo1TS;
    }

    public void setUo1TS(List<UO1T> uo1TS) {
        this.uo1TS = uo1TS;
    }

    public List<PariDispari1T> getPariDispari1TS() {
        return pariDispari1TS;
    }

    public void setPariDispari1TS(List<PariDispari1T> pariDispari1TS) {
        this.pariDispari1TS = pariDispari1TS;
    }

    public List<TTHandicap2T> getTtHandicap2TS() {
        return ttHandicap2TS;
    }

    public void setTtHandicap2TS(List<TTHandicap2T> ttHandicap2TS) {
        this.ttHandicap2TS = ttHandicap2TS;
    }

    public List<SenzaMargine_12_2T> getSenzaMargine_12_2TS() {
        return senzaMargine_12_2TS;
    }

    public void setSenzaMargine_12_2TS(List<SenzaMargine_12_2T> senzaMargine_12_2TS) {
        this.senzaMargine_12_2TS = senzaMargine_12_2TS;
    }

    public List<UO2T> getUo2TS() {
        return uo2TS;
    }

    public void setUo2TS(List<UO2T> uo2TS) {
        this.uo2TS = uo2TS;
    }

    public List<PariDispari2T> getPariDispari2TS() {
        return pariDispari2TS;
    }

    public void setPariDispari2TS(List<PariDispari2T> pariDispari2TS) {
        this.pariDispari2TS = pariDispari2TS;
    }

    public List<UOQuarto_1> getUoQuarto_1s() {
        return uoQuarto_1s;
    }

    public void setUoQuarto_1s(List<UOQuarto_1> uoQuarto_1s) {
        this.uoQuarto_1s = uoQuarto_1s;
    }

    public List<UOQuarto_2> getUoQuarto_2s() {
        return uoQuarto_2s;
    }

    public void setUoQuarto_2s(List<UOQuarto_2> uoQuarto_2s) {
        this.uoQuarto_2s = uoQuarto_2s;
    }

    public List<UOQuarto_3> getUoQuarto_3s() {
        return uoQuarto_3s;
    }

    public void setUoQuarto_3s(List<UOQuarto_3> uoQuarto_3s) {
        this.uoQuarto_3s = uoQuarto_3s;
    }

    public List<UOQuarto_4> getUoQuarto_4s() {
        return uoQuarto_4s;
    }

    public void setUoQuarto_4s(List<UOQuarto_4> uoQuarto_4s) {
        this.uoQuarto_4s = uoQuarto_4s;
    }

    public List<TTHandicap_1Quarto> getTtHandicap_1Quartos() {
        return ttHandicap_1Quartos;
    }

    public void setTtHandicap_1Quartos(List<TTHandicap_1Quarto> ttHandicap_1Quartos) {
        this.ttHandicap_1Quartos = ttHandicap_1Quartos;
    }

    public List<SenzaMargine_12_1Quarto> getSenzaMargine_12_1Quartos() {
        return senzaMargine_12_1Quartos;
    }

    public void setSenzaMargine_12_1Quartos(List<SenzaMargine_12_1Quarto> senzaMargine_12_1Quartos) {
        this.senzaMargine_12_1Quartos = senzaMargine_12_1Quartos;
    }

    public List<PariDispari1Quarto> getPariDispari1Quartos() {
        return pariDispari1Quartos;
    }

    public void setPariDispari1Quartos(List<PariDispari1Quarto> pariDispari1Quartos) {
        this.pariDispari1Quartos = pariDispari1Quartos;
    }

    public List<TTHandicap2Quarto> getTtHandicap2Quartos() {
        return ttHandicap2Quartos;
    }

    public void setTtHandicap2Quartos(List<TTHandicap2Quarto> ttHandicap2Quartos) {
        this.ttHandicap2Quartos = ttHandicap2Quartos;
    }

    public List<SenzaMargine_12_2Quarto> getSenzaMargine_12_2Quartos() {
        return senzaMargine_12_2Quartos;
    }

    public void setSenzaMargine_12_2Quartos(List<SenzaMargine_12_2Quarto> senzaMargine_12_2Quartos) {
        this.senzaMargine_12_2Quartos = senzaMargine_12_2Quartos;
    }

    public List<PariDispari2Quarto> getPariDispari2Quartos() {
        return pariDispari2Quartos;
    }

    public void setPariDispari2Quartos(List<PariDispari2Quarto> pariDispari2Quartos) {
        this.pariDispari2Quartos = pariDispari2Quartos;
    }

    public List<TTHandicap3Quarto> getTtHandicap3Quartos() {
        return ttHandicap3Quartos;
    }

    public void setTtHandicap3Quartos(List<TTHandicap3Quarto> ttHandicap3Quartos) {
        this.ttHandicap3Quartos = ttHandicap3Quartos;
    }

    public List<SenzaMargine_12_3Quarto> getSenzaMargine_12_3Quartos() {
        return senzaMargine_12_3Quartos;
    }

    public void setSenzaMargine_12_3Quartos(List<SenzaMargine_12_3Quarto> senzaMargine_12_3Quartos) {
        this.senzaMargine_12_3Quartos = senzaMargine_12_3Quartos;
    }

    public List<PariDispari3Quarto> getPariDispari3Quartos() {
        return pariDispari3Quartos;
    }

    public void setPariDispari3Quartos(List<PariDispari3Quarto> pariDispari3Quartos) {
        this.pariDispari3Quartos = pariDispari3Quartos;
    }

    public List<TTHandicap4Quarto> getTtHandicap4Quartos() {
        return ttHandicap4Quartos;
    }

    public void setTtHandicap4Quartos(List<TTHandicap4Quarto> ttHandicap4Quartos) {
        this.ttHandicap4Quartos = ttHandicap4Quartos;
    }

    public List<SenzaMargine_12_4Quarto> getSenzaMargine_12_4Quartos() {
        return senzaMargine_12_4Quartos;
    }

    public void setSenzaMargine_12_4Quartos(List<SenzaMargine_12_4Quarto> senzaMargine_12_4Quartos) {
        this.senzaMargine_12_4Quartos = senzaMargine_12_4Quartos;
    }

    public List<PariDispari4Quarto> getPariDispari4Quartos() {
        return pariDispari4Quartos;
    }

    public void setPariDispari4Quartos(List<PariDispari4Quarto> pariDispari4Quartos) {
        this.pariDispari4Quartos = pariDispari4Quartos;
    }

    public List<UOCaseInclSuppl> getUoCaseInclSuppls() {
        return uoCaseInclSuppls;
    }

    public void setUoCaseInclSuppls(List<UOCaseInclSuppl> uoCaseInclSuppls) {
        this.uoCaseInclSuppls = uoCaseInclSuppls;
    }

    public List<UOOspiteInclSuppl> getUoOspiteInclSuppls() {
        return uoOspiteInclSuppls;
    }

    public void setUoOspiteInclSuppls(List<UOOspiteInclSuppl> uoOspiteInclSuppls) {
        this.uoOspiteInclSuppls = uoOspiteInclSuppls;
    }

    public List<TempoFinale_1> getTempoFinale_1s() {
        return tempoFinale_1s;
    }

    public void setTempoFinale_1s(List<TempoFinale_1> tempoFinale_1s) {
        this.tempoFinale_1s = tempoFinale_1s;
    }

    public List<QuartoConPuntPiuAlto> getQuartoConPuntPiuAltos() {
        return quartoConPuntPiuAltos;
    }

    public void setQuartoConPuntPiuAltos(List<QuartoConPuntPiuAlto> quartoConPuntPiuAltos) {
        this.quartoConPuntPiuAltos = quartoConPuntPiuAltos;
    }
}
