package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.PariDispari3Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PariDispari3QuartoRepo extends JpaRepository<PariDispari3Quarto,Integer> {
}
