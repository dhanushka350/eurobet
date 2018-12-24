package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TTUOInclSuppl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTUOInclSupplRepo extends JpaRepository<TTUOInclSuppl, Integer> {
}
