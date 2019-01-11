package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.ScrapeLinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapeLinksRepo extends JpaRepository<ScrapeLinks, Integer> {

    ScrapeLinks findTopByNameEquals(String name);
}
