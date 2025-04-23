package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// Koti sivu
@AnonymousAllowed
@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView() {

        //  kirjautumistilan 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");

        if (isLoggedIn) {//jos kirjautunut näyttää tämän
            String username = auth.getName();
            add(new Paragraph("Olet kirjautunut sisään käyttäjänä: " + username));

            //uloskirjaus logiikka
            Button logoutButton = new Button("Kirjaudu ulos", event -> {
                SecurityContextHolder.clearContext();   
                VaadinSession.getCurrent().getSession().invalidate();
                UI.getCurrent().getSession().close();
                UI.getCurrent().getPage().setLocation("/");
            });
            logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            add(logoutButton);
        } 
        else { //jos ei kirjautunut näyttää tämän
            add(new Paragraph("Et ole kirjautunut sisään"));
            add(new Paragraph("Käyttäjät: user ja admin"));
            add(new Paragraph("Salasanat: userpass ja adminpass"));

            Button loginButton = new Button("Kirjaudu sisään", event -> {
                UI.getCurrent().getPage().setLocation("/login");
            });
            loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            add(loginButton);
        }
    }
}