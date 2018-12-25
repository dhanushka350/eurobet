package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.PariDispari1T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PariDispari1TRepo extends JpaRepository<PariDispari1T, Integer> {
}
