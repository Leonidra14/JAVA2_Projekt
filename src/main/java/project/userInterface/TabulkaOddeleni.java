package project.userInterface;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.IntegerField;
import org.springframework.beans.factory.annotation.Value;
import com.vaadin.flow.component.dialog.Dialog;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import project.business.dto.DivisionDTO;

@Route("tabulkaOddeleni")
public class TabulkaOddeleni extends VerticalLayout {
    private String url;
    public TabulkaOddeleni(@Value("${backend.url}") String url) {
        this.url = url;

        Label nadpis = new Label("Toto jsou vsechna oddeleni daneho MU");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);

        add(new MenuZamestnance(url));

        //private  Long ID;
        //private String comp;

        Grid<DivisionDTO> grid = new Grid<>(DivisionDTO.class, false);
        grid.addColumn(DivisionDTO::getID).setHeader("ID oddeleni").setSortable(true);
        grid.addColumn(DivisionDTO::getComp).setHeader("Kompetence oddeleni").setSortable(true);

        var res = ClientBuilder.newClient()
                .target(url)
                .path("/oddeleni")
                .queryParam("flagReadInOrderFromOldest", true)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Response.class);

        List<DivisionDTO> oddeleni = res.readEntity(new GenericType<List<DivisionDTO>>(){});

        grid.setItems(oddeleni);
        add(grid);
        setAlignItems(Alignment.CENTER);
        add(grid);

        //mazani
        IntegerField idField = new IntegerField("ID oddeleni, ktere chcete zrusit");
        Button deleteButton = new Button("Smazat");

        deleteButton.addClickListener(e -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle(String.format("Jste si jisti, ze chcete dane oddeleni zrusit?"));

            Button confirmButton = new Button("Ano");
            Button noButton = new Button("Ne");

            //listener to confirm button
            confirmButton.addClickListener(event -> {
                Long id = (long) idField.getValue();
                DivisionDTO serverResponse = delete(id);
                dialog.close();
                getUI().ifPresent(ui-> ui.navigate("menuZamestnance"));
            });

            //listener to noButton
            noButton.addClickListener(event -> {
                dialog.close();
                getUI().ifPresent(ui-> ui.navigate("tabulkaOddeleni"));
            });


            dialog.getFooter().add(confirmButton, noButton);
            dialog.open();
            add(dialog);
        });
        add(idField, deleteButton);
    }

    DivisionDTO delete(Long id) {
        return ClientBuilder.newClient()
                .target(url)
                .path("/oddeleni/" + id)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete(DivisionDTO.class);
    }

}

