package project.userInterface;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.AddressDTO;
import project.business.dto.ProjectDTO;
import com.vaadin.flow.component.dialog.Dialog;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Log4j2
@Route("tabulkaProjekty")
public class TabulkaProjekty extends VerticalLayout {
    private String url;
    public TabulkaProjekty(@Value("${backend.url}") String url) {
        this.url = url;

        Label nadpis = new Label("Toto jsou vsechny projekty daneho MU");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);

        add(new MenuZamestnance(url));



        Grid<ProjectDTO> grid = new Grid<>(ProjectDTO.class, false);
        grid.addColumn(ProjectDTO::getID).setHeader("ID projektu").setSortable(true);
        grid.addColumn(ProjectDTO::getName).setHeader("Název").setSortable(true);
        grid.addColumn(ProjectDTO::getStart_date).setHeader("Začátek projektu").setSortable(true);
        grid.addColumn(ProjectDTO::getEnd_date).setHeader("Konec projektu").setSortable(true);
        //grid.setItems(projekty);


        var res = ClientBuilder.newClient()
                .target(url)
                .path("/projekt")
                .queryParam("flagReadInOrderFromOldest", true)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Response.class);

        List<ProjectDTO> projekty = res.readEntity(new GenericType<List<ProjectDTO>>(){});

        grid.setItems(projekty);
        add(grid);
        setAlignItems(Alignment.CENTER);
        add(grid);

        //mazani
        IntegerField idField = new IntegerField("ID projektu, ktereho chcete vymazat");
        Button deleteButton = new Button("Smazat");

        deleteButton.addClickListener(e -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle(String.format("Jste si jisti, ze chcete dany projekt smazat?"));

            Button confirmButton = new Button("Ano");
            Button noButton = new Button("Ne");

            //listener to confirm button
            confirmButton.addClickListener(event -> {
                Long id = (long) idField.getValue();
                ProjectDTO serverResponse = delete(id);
                dialog.close();
                getUI().ifPresent(ui-> ui.navigate("menuZamestnance"));
            });

            //listener to noButton
            noButton.addClickListener(event -> {
                dialog.close();
                getUI().ifPresent(ui-> ui.navigate("tabulkaProjekty"));
            });


            dialog.getFooter().add(confirmButton, noButton);
            dialog.open();
            add(dialog);
        });
        add(idField, deleteButton);


        //pridavam
        LocalDateTime ted = LocalDateTime.now();
        DatePicker dateTimePicker = new DatePicker("Pouzij si pro kontrolu poctu dnu");

        add(dateTimePicker);

        Label vysledekLabel = new Label();
        add(vysledekLabel);

        Button prevod = new Button("Preved");
        prevod.addClickListener( event -> {
            var zkouska = dateTimePicker.getValue();
            log.warn("zko" + zkouska);
            LocalDateTime dateTime = dateTimePicker.getValue().atStartOfDay();
            long result = ChronoUnit.DAYS.between(ted, dateTime);
            log.warn("ted"+ ted);
            log.warn(result);
            vysledekLabel.setText("Počet dnů mezi dneškem a zadaným dnem: " + result);
                }
        );


        add(prevod);

    }

    ProjectDTO delete(Long id) {
        return ClientBuilder.newClient()
                .target(url)
                .path("/projekt/" + id)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete(ProjectDTO.class);
    }

}
