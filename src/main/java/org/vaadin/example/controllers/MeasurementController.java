package org.vaadin.example.controllers;

import org.vaadin.example.entities.Measurement;
import org.vaadin.example.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// HTTP-pyynnöt mittausdatalle
@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    @Autowired
    private  MeasurementService measurementService;
    
    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.getAllMeasurements();
    }

    @GetMapping("/person/{personId}")
    public List<Measurement> getMeasurementsByPerson(@PathVariable Long personId) {
        return measurementService.getMeasurementsByPersonId(personId);
    }

    @PostMapping
    public Measurement createMeasurement(@RequestBody Measurement measurement) {
        return measurementService.saveMeasurement(measurement);
    }
}