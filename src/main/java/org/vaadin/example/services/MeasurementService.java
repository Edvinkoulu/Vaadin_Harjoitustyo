package org.vaadin.example.services;

import org.vaadin.example.entities.Measurement;
import org.vaadin.example.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// mahdollistaa mittausten hakemisen ja uusien mittausten tallentamisen kantaan
@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public List<Measurement> getMeasurementsByPersonId(Long personId) {
        return measurementRepository.findByPersonId(personId);
    }

    public Measurement saveMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }
}