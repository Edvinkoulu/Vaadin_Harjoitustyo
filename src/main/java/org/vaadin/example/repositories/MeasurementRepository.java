package org.vaadin.example.repositories;

import org.vaadin.example.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// vastaa Measurement-entiteetin tietokantaoperaatioista
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByPersonId(Long personId);  // Etsii mittaukset henkilön ID:llä
}
