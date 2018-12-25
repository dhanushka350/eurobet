package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TTHandicap2T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTHandicap2TRepo extends JpaRepository<TTHandicap2T, Integer> {
}
