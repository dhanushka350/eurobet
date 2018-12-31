package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.Scoure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoureRepo extends JpaRepository<Scoure,Integer> {
}
