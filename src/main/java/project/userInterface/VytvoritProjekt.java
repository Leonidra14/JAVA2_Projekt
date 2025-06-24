package project.userInterface;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.ProjectDTO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Route(value = "vytvoritProjekt")
public class VytvoritProjekt  extends VerticalLayout {
    String url;
    public VytvoritProjekt(@Value("${backend.url}") String url){
        this.url = url;

        Label nadpis = new Label("Vytvoreni noveho projektu");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);
        //    private Long ID;
        //    private String name;
        //    private Date start_date;
        //    private Date end_date;
        TextField name = new TextField("Jmeno projektu");
        //TextField startDate = new TextField("Zacatek projektu");
        DatePicker startDate = new DatePicker();
        startDate.setLabel("Začátek projektu");
        startDate.setPlaceholder("Vyberte datum");

        DatePicker endDate = new DatePicker();
        endDate.setLabel("Konec projektu");
        endDate.setPlaceholder("Vyberte datum");
        add(endDate);
        add(name, startDate, endDate);

        Button button = new Button("Potvrdit");
        button.addClickListener( event -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setName(name.getValue());
                    Date sdate = java.sql.Date.valueOf(startDate.getValue());
                    Date edate = java.sql.Date.valueOf(endDate.getValue());
                    dto.setStart_date(sdate);
                    dto.setEnd_date(edate);

                    dto.setID(null);

                    ProjectDTO serverResponse = create(dto);

                    Dialog dialog = new Dialog();
                    dialog.setHeaderTitle(String.format("Projekt byl uspesne vytvoren"));
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

    ProjectDTO create(ProjectDTO inputRegistrationForm){
        return ClientBuilder.newClient()
                .target(url)
                .path("/projekt")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(inputRegistrationForm, MediaType.APPLICATION_JSON_TYPE), ProjectDTO.class);
    }
}