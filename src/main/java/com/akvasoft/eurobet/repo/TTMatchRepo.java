package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TTMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTMatchRepo extends JpaRepository<TTMatch, Integer> {
}
