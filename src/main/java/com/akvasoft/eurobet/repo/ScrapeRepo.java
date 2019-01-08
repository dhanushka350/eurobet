package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.Scrape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeRepo extends JpaRepository<Scrape, Integer> {
}
