package org.vaadin.example.repositories;

import org.vaadin.example.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;


// Vastaa Person-entiteetin tietokantaoperaatioista perimällä JpaRepositoryn
public interface PersonRepository extends JpaRepository<Person, Long> {

}