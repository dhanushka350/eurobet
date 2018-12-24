package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TempiRegolam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempiRegolamRepo extends JpaRepository<TempiRegolam, Integer> {
}
