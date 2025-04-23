package org.vaadin.example.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

//joka sivulle sama header ja footer. headerissa navigointi
@CssImport("themes/my-theme/mainLayout.css")
public class MainLayout extends VerticalLayout implements RouterLayout {

    private final HorizontalLayout footer = createFooter(); 

    public MainLayout() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();

        add(createHeader());
        add(footer);
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.addClassName("header-style");

        RouterLink homeLink = new RouterLink("Koti", MainView.class);
        RouterLink personLink = new RouterLink("Henkilöt", PersonView.class);
        RouterLink measurementLink = new RouterLink("Mittaukset", MeasurementView.class);

        header.add(homeLink, personLink, measurementLink);
        return header;
    }

    private HorizontalLayout createFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setPadding(true);
        footer.add("© 2025 Vaadin Flow Application");
        footer.getStyle().set("background-color", "lightblue");

        return footer;
    }

    //tyhjentää sivun keskiosan vanhasta sisällöstä ja lisää uuden sisällön
    @Override
    public void showRouterLayoutContent(HasElement content) {
        getChildren()
            .filter(component -> !(component instanceof HorizontalLayout)) 
            .forEach(this::remove);

        Component viewContent = (Component) content;
        addComponentAtIndex(1, viewContent); 
        expand(viewContent);
    }
}