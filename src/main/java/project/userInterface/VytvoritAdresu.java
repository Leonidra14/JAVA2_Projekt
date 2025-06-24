package project.userInterface;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.AddressDTO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@Route(value = "vytvoritAdresu")
public class VytvoritAdresu  extends VerticalLayout {
    String url;
    public VytvoritAdresu(@Value("${backend.url}") String url){
        this.url = url;

        Label nadpis = new Label("Vytvoreni nove adresy");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);
        // private Long IDadd;
        //    private String state;
        //    private String city;
        //    private Integer house_num;
        //    private Integer ZIP;
        TextField state = new TextField("Stat");
        TextField city = new TextField("mesto");
        IntegerField house_num = new IntegerField("Cislo domu");
        IntegerField ZIP = new IntegerField("PSC");

        add(state, city, house_num, ZIP);

        Button button = new Button("Potvrdit");
        button.addClickListener( event -> {
                    AddressDTO dto = new AddressDTO();
                    dto.setState(state.getValue());
                    dto.setCity(city.getValue());
                    dto.setZIP(ZIP.getValue());
                    dto.setHouse_num(house_num.getValue());

                    dto.setIDadd(null);

                    AddressDTO serverResponse = create(dto);

                    Dialog dialog = new Dialog();
                    dialog.setHeaderTitle(String.format("Adresa byla uspesne pridana"));
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

    AddressDTO create(AddressDTO inputRegistrationForm){
        return ClientBuilder.newClient()
                .target(url)
                .path("/adresa")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(inputRegistrationForm, MediaType.APPLICATION_JSON_TYPE), AddressDTO.class);
    }
}


