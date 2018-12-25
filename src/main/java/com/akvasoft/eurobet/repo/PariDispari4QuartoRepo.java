package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.PariDispari4Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PariDispari4QuartoRepo extends JpaRepository<PariDispari4Quarto, Integer> {
}
