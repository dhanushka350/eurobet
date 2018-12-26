package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UltimaSquadraSegnare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UltimaSquadraSegnareRepo extends JpaRepository<UltimaSquadraSegnare, Integer> {
}
