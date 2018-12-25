package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UOOspiteInclSuppl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOOspiteInclSupplRepo extends JpaRepository<UOOspiteInclSuppl, Integer> {
}
