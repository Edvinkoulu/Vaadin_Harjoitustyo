package org.vaadin.example.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;


//kirjautumis näkymä
@PageTitle("Kirjaudu sisään")
@Route(value ="login", layout = MainLayout.class)
@PermitAll
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        
        H1 title = new H1("Kirjaudu sisään nähdäksesi sisältö");
        title.getStyle()//Tyylien muokkaaminen näkymässä yksittäiselle komponentille
        .set("color", "darkblue")
        .set("font-size", "2rem")
        .set("margin-top", "20px");


        
        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login"); // Tämä on OK Spring Securityn kanssa
        loginForm.getStyle()//Tyylien muokkaaminen suoraan yksittäiselle komponentille
        .set("box-shadow", "0 4px 10px rgba(0,0,0,0.1)")
        .set("padding", "20px")
        .set("border-radius", "10px")
        .set("background-color", "#f9f9f9");

        add(title, loginForm);
    }
    
}