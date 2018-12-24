package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.UOInclSuppl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOInclSupplRepo extends JpaRepository<UOInclSuppl,Integer> {
}
