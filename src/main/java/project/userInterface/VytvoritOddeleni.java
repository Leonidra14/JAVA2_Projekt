package project.userInterface;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.DivisionDTO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@Route(value = "vytvoritOddeleni")
public class VytvoritOddeleni  extends VerticalLayout {
    String url;
    public VytvoritOddeleni(@Value("${backend.url}") String url){
        this.url = url;

        Label nadpis = new Label("Vytvoreni noveho oddeleni");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);
        Label text = new Label("Prosime pridejte kompetence tohoto oddeleni");
        nadpis.getElement().getStyle().set("font-size", "50px");
        add(text);
        TextField comp = new TextField("Kompetence");


        add(comp);

        Button button = new Button("Potvrdit");
        button.addClickListener( event -> {
                    DivisionDTO dto = new DivisionDTO();
                    dto.setComp(comp.getValue());

                    dto.setID(null);

                    DivisionDTO serverResponse = create(dto);

                    Dialog dialog = new Dialog();
                    dialog.setHeaderTitle(String.format("Oddeleni bylo uspesne pridano"));
                    Button okButton = new Button("Zpet na menu");
                    okButton.addClickListener(e ->
                            okButton.getUI().ifPresent(ui ->
                                    ui.navigate("menuZamestnance"))
                    );
                    okButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                    dialog.getFooter().add(okButton);

                    dialog.open();
                    add(dialog);
                }
        );
        add(button);

        setAlignItems(Alignment.CENTER);
    }

    DivisionDTO create(DivisionDTO inputRegistrationForm){
        return ClientBuilder.newClient()
                .target(url)
                .path("/oddeleni")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(inputRegistrationForm, MediaType.APPLICATION_JSON_TYPE), DivisionDTO.class);
    }
}
