package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.PariDispari2Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PariDispari2QuartoRepo extends JpaRepository<PariDispari2Quarto,Integer> {
}
