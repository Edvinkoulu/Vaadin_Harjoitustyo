package org.vaadin.example.entities;

import jakarta.persistence.*;

// Mittaus entiteetti, yhdistyy kannan Measurement tauluun 
@Entity
public class Measurement {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String measurementValue;

    @ManyToOne
    @JoinColumn(name = "person_id")  
    private Person person;

    public Measurement() {} 

    public Measurement(String type, String measurementValue, Person person) {
        this.type = type;
        this.measurementValue = measurementValue;
        this.person = person;
    }

    
    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMeasurementValue() { return measurementValue; }
    public void setMeasurementValue(String measurementValue) { this.measurementValue = measurementValue; }

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
}