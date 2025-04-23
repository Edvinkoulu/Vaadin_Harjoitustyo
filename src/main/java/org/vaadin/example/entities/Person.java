package org.vaadin.example.entities;

import java.util.List;
import jakarta.persistence.*;

// henkilö entiteetti, yhdistyy kannan Person tauluun.
@Entity
public class Person {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public Person() {}

    public Person(String name){
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

