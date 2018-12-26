package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.ComboMatchUltimoPunto;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboMatchUltimoPuntoRepo extends JpaRepository<ComboMatchUltimoPunto,Integer> {
}
