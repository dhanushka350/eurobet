package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.Supplementari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementariRepo extends JpaRepository<Supplementari, Integer> {
}
