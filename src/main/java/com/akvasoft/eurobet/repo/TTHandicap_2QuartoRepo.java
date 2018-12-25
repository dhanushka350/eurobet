package com.akvasoft.eurobet.repo;

import com.akvasoft.eurobet.modals.TTHandicap2Quarto;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TTHandicap_2QuartoRepo extends JpaRepository<TTHandicap2Quarto,Integer> {
}
