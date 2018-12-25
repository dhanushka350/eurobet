package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.QuartoConPuntPiuAlto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartoConPuntPiuAltoRepo extends JpaRepository<QuartoConPuntPiuAlto, Integer> {
}
