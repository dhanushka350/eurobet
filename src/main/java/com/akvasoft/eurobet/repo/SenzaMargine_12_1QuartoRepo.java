package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.SenzaMargine_12_1Quarto;
import com.akvasoft.eurobet.modals.SenzaMargine_12_1T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenzaMargine_12_1QuartoRepo extends JpaRepository<SenzaMargine_12_1Quarto, Integer> {
}
