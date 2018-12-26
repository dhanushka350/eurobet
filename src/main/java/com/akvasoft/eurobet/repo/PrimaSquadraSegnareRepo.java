package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.PrimaSquadraSegnare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaSquadraSegnareRepo extends JpaRepository<PrimaSquadraSegnare, Integer> {
}
