package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.entities.Measurement;
import org.vaadin.example.entities.Person;
import org.vaadin.example.services.PersonService;
import org.vaadin.example.services.MeasurementService;
import org.springframework.web.client.RestTemplate;
import java.util.List;

// Henkilöt sivu
@Route(value = "persons", layout = MainLayout.class)
@PermitAll  
public class PersonView extends VerticalLayout {
    
    private final Grid<Person> personGrid = new Grid<>();
    private final PersonService personService;
    private final MeasurementService measurementService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String MEASUREMENTS_API_URL = "http://localhost:8080/api/measurements";

    @Autowired
    public PersonView(PersonService personService, MeasurementService measurementService) {
        this.personService = personService;
        this.measurementService = measurementService;
        setSizeFull();

        List<Person> personList = personService.getAllPersons();

        TextField nameField = new TextField("Nimi");
        Button addButton = new Button("Lisää henkilö", event -> addPerson(nameField));

        personGrid.setItems(personList);
        personGrid.addColumn(Person::getName).setHeader("Nimi").setSortable(true);

        // lisätään napit joka riville
        personGrid.addComponentColumn(person -> {
            Button viewButton = new Button("Katso mittauksia", event -> openMeasurementsDialog(person));
            Button addButtonRow = new Button("Lisää mittaus", event -> openMeasurementDialog(person));
            return new HorizontalLayout(viewButton, addButtonRow);
        }).setHeader("Toiminnot");

        add(nameField, addButton, personGrid);
    }

    // Lisää henkilö
    private void addPerson(TextField nameField) {
        String name = nameField.getValue().trim();

        if (name.isEmpty()) {
            Notification.show("Nimi ei voi olla tyhjä!");
            return;
        }

        Person newPerson = new Person(name);
        personService.savePerson(newPerson);

        personGrid.setItems(personService.getAllPersons());
        nameField.clear();
        Notification.show("Henkilö lisätty!");
    }

    // Avaa dialogin henkilön mittausten katseluun
    private void openMeasurementsDialog(Person person) {
        Dialog dialog = new Dialog();
        dialog.setWidth("500px");
        dialog.setHeaderTitle("Mittaukset: " + person.getName());

        Grid<Measurement> measurementGrid = new Grid<>();
        measurementGrid.addColumn(Measurement::getType).setHeader("Mitattu ominaisuus");
        measurementGrid.addColumn(Measurement::getMeasurementValue).setHeader("Mittatulos"); 

        List<Measurement> measurements = fetchPersonMeasurements(person);
        measurementGrid.setItems(measurements);

        Button closeButton = new Button("Sulje", event -> dialog.close());

        dialog.add(measurementGrid, closeButton);
        dialog.open();
    }

    // Hakee mittaukset henkilölle
    private List<Measurement> fetchPersonMeasurements(Person person) {
        try {
            Measurement[] measurements = restTemplate.getForObject(
                    MEASUREMENTS_API_URL + "/person/" + person.getId(),
                    Measurement[].class);

            if (measurements != null) {
                return List.of(measurements);
            } else {
                Notification.show("Ei mittauksia tälle henkilölle");
                return List.of();
            }
        } catch (Exception e) {
            Notification.show("Virhe haettaessa mittauksia");
            return List.of();
        }
    }

    // Avaa dialogin mittauksen lisäämiseen
    private void openMeasurementDialog(Person person) {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");
        dialog.setHeaderTitle("Lisää mittaus henkilölle: " + person.getName());

        TextField typeField = new TextField("Mitattava ominaisuus");
        TextField valueField = new TextField("Mittatulos");

        Button saveButton = new Button("Tallenna", event -> {
            addMeasurement(typeField, valueField, person);
            dialog.close(); 
        });
        dialog.add(typeField, valueField, saveButton);
        dialog.open();
    }

    // Lisää mittaus
    private void addMeasurement(TextField typeField, TextField valueField, Person person) {
        
        try {
            String type = typeField.getValue();
            String measurementValue = valueField.getValue(); 
    
            if (type == null || type.isEmpty() || measurementValue == null || measurementValue.isEmpty()) {
                Notification.show("Täytä kaikki kentät!");
                return;
            }

            Measurement newMeasurement = new Measurement(type, measurementValue, person);
            Measurement savedMeasurement = measurementService.saveMeasurement(newMeasurement);

            if (savedMeasurement != null) {
                Notification.show("Mittaus lisätty!");
            } else {
                Notification.show("Mittauksen lisäys epäonnistui!");
            }
    
            typeField.clear();
            valueField.clear();
    
        } catch (Exception e) {
            Notification.show("Virhe tallennettaessa mittausta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}