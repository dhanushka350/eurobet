package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UO1T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UO1TRepo extends JpaRepository<UO1T, Integer> {
}
