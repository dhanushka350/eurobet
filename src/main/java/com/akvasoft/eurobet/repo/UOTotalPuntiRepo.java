package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UOTotalPunti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOTotalPuntiRepo extends JpaRepository<UOTotalPunti, Integer> {
}
