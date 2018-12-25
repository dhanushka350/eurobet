package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TTHandicap1T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTHandicap1TRepo extends JpaRepository<TTHandicap1T, Integer> {
}
