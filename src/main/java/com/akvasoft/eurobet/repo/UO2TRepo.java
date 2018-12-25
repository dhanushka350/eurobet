package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UO2T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UO2TRepo extends JpaRepository<UO2T,Integer> {
}
