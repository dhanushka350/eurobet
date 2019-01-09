package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.ScrapeMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeMatchRepo extends JpaRepository<ScrapeMatch, Integer> {
}
