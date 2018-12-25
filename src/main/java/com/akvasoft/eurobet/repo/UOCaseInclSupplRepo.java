package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UOCaseInclSuppl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOCaseInclSupplRepo extends JpaRepository<UOCaseInclSuppl, Integer> {
}
