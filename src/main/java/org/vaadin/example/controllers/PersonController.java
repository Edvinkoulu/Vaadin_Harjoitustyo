package org.vaadin.example.controllers;

import org.vaadin.example.entities.Person; 
import org.vaadin.example.services.PersonService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// HTTP-pyynnöt henkilödatalle
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;  

    @GetMapping
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }
}