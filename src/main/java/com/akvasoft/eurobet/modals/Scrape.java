package com.akvasoft.eurobet.modals;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "T_SCRAPE")
public class Scrape {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "T_SCRAPE_ID")
    private int id;

    @Column(name = "T_SCRAPE_TIME")
    private String time;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scrape", fetch = FetchType.LAZY)
    private List<Match> matches = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}
