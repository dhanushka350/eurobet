package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.PariDispari2T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PariDispari2TRepo extends JpaRepository<PariDispari2T, Integer> {
}
