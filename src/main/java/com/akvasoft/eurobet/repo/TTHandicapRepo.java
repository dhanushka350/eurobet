package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TTHandicap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTHandicapRepo extends JpaRepository<TTHandicap, Integer> {
}
