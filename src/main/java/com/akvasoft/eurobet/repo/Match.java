package com.akvasoft.eurobet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Match extends JpaRepository<com.akvasoft.eurobet.modals.Match,Integer> {
}
