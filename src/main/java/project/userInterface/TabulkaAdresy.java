package project.userInterface;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.model.Dial;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.IntegerField;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.AddressDTO;
import com.vaadin.flow.component.dialog.Dialog;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("tabulkaAdresy")
public class TabulkaAdresy extends VerticalLayout {
    private String url;
    public TabulkaAdresy(@Value("${backend.url}") String url) {
        this.url = url;

        Label nadpis = new Label("Toto jsou vsechny adresy evidovane v MU");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);

        add(new MenuZamestnance(url));



        Grid<AddressDTO> grid = new Grid<>(AddressDTO.class, false);
        grid.addColumn(AddressDTO::getIDadd).setHeader("ID adresy").setSortable(true);
        grid.addColumn(AddressDTO::getState).setHeader("Stat").setSortable(true);
        grid.addColumn(AddressDTO::getCity).setHeader("Mesto").setSortable(true);
        grid.addColumn(AddressDTO::getHouse_num).setHeader("Cislo domu").setSortable(true);
        grid.addColumn(AddressDTO::getZIP).setHeader("PSC").setSortable(true);

        var res = ClientBuilder.newClient()
                .target(url)
                .path("/adresa")
                .queryParam("flagReadInOrderFromOldest", true)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Response.class);

        List<AddressDTO> adresy = res.readEntity(new GenericType<List<AddressDTO>>(){});

        grid.setItems(adresy);
        add(grid);
        setAlignItems(Alignment.CENTER);
        add(grid);

        //mazani
        IntegerField idField = new IntegerField("ID adresy, ktere chcete vymazat");
        Button deleteButton = new Button("Smazat");

        deleteButton.addClickListener(e -> {
            Dialog dialog = new Dialog();
            dialog.setHeaderTitle(String.format("Jste si jisti, ze chcete danou adresu smazat?"));

            Button confirmButton = new Button("Ano");
            Button noButton = new Button("Ne");

            //listener to confirm button
            confirmButton.addClickListener(event -> {
                Long id = (long) idField.getValue();
                AddressDTO serverResponse = delete(id);
                dialog.close();
                getUI().ifPresent(ui-> ui.navigate("menuZamestnance"));
            });

            //listener to noButton
            noButton.addClickListener(event -> {
                dialog.close();
                getUI().ifPresent(ui-> ui.navigate("tabulkaAdresy"));
            });


            dialog.getFooter().add(confirmButton, noButton);
            dialog.open();
            add(dialog);
        });
        add(idField, deleteButton);
    }

    AddressDTO delete(Long id) {
        return ClientBuilder.newClient()
                .target(url)
                .path("/adresa/" + id)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete(AddressDTO.class);
    }

}

