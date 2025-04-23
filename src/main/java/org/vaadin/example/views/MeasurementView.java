package org.vaadin.example.views;

import org.vaadin.example.entities.Measurement;
import org.vaadin.example.entities.Person;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;


// Mittaukset sivu
@Route(value = "measurements/:personId?", layout = MainLayout.class)
@PermitAll
public class MeasurementView extends VerticalLayout {

    private final Grid<Measurement> measurementGrid = new Grid<>(Measurement.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String MEASUREMENTS_API_URL = "http://localhost:8080/api/measurements";
    private final String PERSONS_API_URL = "http://localhost:8080/api/persons";

    //henkilö valitaan comboboxista
    private final ComboBox<Person> personComboBox = new ComboBox<>("Valitse henkilö");

    public MeasurementView() {
        setSizeFull();

        // tarkistaa onko käyttäjä admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null &&
                          authentication.getAuthorities().stream()
                              .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // näytetään viesti ei-adminille
            Notification.show("Vain admin näkee mittaukset.");
            add(new com.vaadin.flow.component.html.Paragraph("Vain admin näkee mittaukset."));
            return;
        }
        //mittauksen lisäämiseen
        TextField typeField = new TextField("Mitattava ominaisuus");
        TextField valueField = new TextField("Mittatulos");
        Button addButton = new Button("Lisää mittaus", event -> addMeasurement(typeField, valueField));

        fetchPersons();
        fetchMeasurements(null);

        personComboBox.setItemLabelGenerator(Person::getName);
        personComboBox.setPlaceholder("Henkilö");
        personComboBox.addValueChangeListener(event -> fetchMeasurements(event.getValue()));

        measurementGrid.setColumns("type", "measurementValue");
        measurementGrid.addColumn(measurement -> measurement.getPerson() != null ? measurement.getPerson().getName() : "Tuntematon")
                .setHeader("Henkilö")
                .setSortable(true);

        add(typeField, valueField, personComboBox, addButton, measurementGrid);
    }
        //hakee mittaukset
    private void fetchMeasurements(Person person) {
        try {
            String url = MEASUREMENTS_API_URL;
            if (person != null) {
                url += "/person/" + person.getId();
            }
            Measurement[] measurements = restTemplate.getForObject(url, Measurement[].class);
            if (measurements != null) {
                measurementGrid.setItems(Arrays.asList(measurements));
            }
        } catch (Exception e) {
            Notification.show("Virhe haettaessa mittauksia: " + e.getMessage());
            e.printStackTrace();
        }
    }
        //hakee henkilöt 
    private void fetchPersons() {
        try {
            Person[] persons = restTemplate.getForObject(PERSONS_API_URL, Person[].class);
            if (persons != null) {
                personComboBox.setItems(Arrays.asList(persons));
            } else {
                Notification.show("Ei henkilöitä löytynyt");
            }
        } catch (Exception e) {
            Notification.show("Virhe haettaessa henkilöitä: " + e.getMessage());
            e.printStackTrace();
        }
    }

        // mittauksen lisäys
    private void addMeasurement(TextField typeField, TextField valueField) {
        try {
            String type = typeField.getValue();
            String measurementValue = valueField.getValue();
            Person selectedPerson = personComboBox.getValue();

            if (type.isEmpty() || measurementValue == null || selectedPerson == null) {
                Notification.show("Täytä kaikki kentät ja valitse henkilö!");
                return;
            }

            Measurement newMeasurement = new Measurement(type, measurementValue, selectedPerson);
            restTemplate.postForObject(MEASUREMENTS_API_URL, newMeasurement, Measurement.class);
            fetchMeasurements(selectedPerson);

            typeField.clear();
            valueField.clear();
            personComboBox.clear();
            Notification.show("Mittaus lisätty!");
        } catch (Exception e) {
            Notification.show("Virhe tallennettaessa mittausta");
        }
    }
}