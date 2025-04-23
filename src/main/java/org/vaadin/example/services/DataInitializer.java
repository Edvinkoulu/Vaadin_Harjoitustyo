package org.vaadin.example.services;

import org.vaadin.example.entities.Person;
import org.vaadin.example.entities.Measurement;
import org.vaadin.example.entities.User;
import org.vaadin.example.repositories.PersonRepository;
import org.vaadin.example.repositories.MeasurementRepository;
import org.vaadin.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Set;


// Alustaa dataa kantaan, jos kanta on tyhjänä
@Service
public class DataInitializer {

    private final PersonRepository personRepository;
    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(
            PersonRepository personRepository,
            MeasurementRepository measurementRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.personRepository = personRepository;
        this.measurementRepository = measurementRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        if (personRepository.count() == 0) {
            // henkilöt ja mittaukset
            Person p1 = new Person("Mikko Meikäläinen");
            Person p2 = new Person("Maija Mallikas");
            Person p3 = new Person("Kari Kokki");
            Person p4 = new Person("Liisa Lapsi");
            Person p5 = new Person("Pekka Pekkanen");

            personRepository.saveAll(List.of(p1, p2, p3, p4, p5));

            List<Measurement> measurements = List.of(
                new Measurement("Paino", "72.5 kg", p1),
                new Measurement("Pituus", "180.2 cm", p1),
                new Measurement("Sykemittaus", "65.3 bpm", p2),
                new Measurement("Paino", "95.1 kg", p3),
                new Measurement("Pituus", "165.8 cm", p4),
                new Measurement("Sykemittaus", "75.0 bpm", p5)
            );
            measurementRepository.saveAll(measurements);
        }

        if (userRepository.count() == 0) {
            // käyttäjien alustaminen
            User admin = new User(
                "admin",
                passwordEncoder.encode("adminpass"),
                Set.of("ADMIN", "USER")
            );

            User normalUser = new User(
                "user",
                passwordEncoder.encode("userpass"),
                Set.of("USER")
            );
            
            userRepository.saveAll(List.of(admin, normalUser));
        }
    }
}