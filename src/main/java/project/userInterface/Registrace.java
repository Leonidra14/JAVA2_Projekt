package project.userInterface;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.EmployeeDTO;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@Route(value = "registrace")
public class Registrace  extends VerticalLayout {
    String url;
    public Registrace(@Value("${backend.url}") String url){
        this.url = url;

        Label nadpis = new Label("Registrace");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);
        //private Long ID;
        //    private String fname;
        //    private String lname;
        //
        //    private  Character sex;
        //
        //    private String email;
        //
        //    private Integer work_phone;
        TextField jmeno = new TextField("Jméno");
        TextField prijmeni = new TextField("Příjmení");
        TextField email = new TextField("Email");
        IntegerField telefon = new IntegerField("Telefon");

        RadioButtonGroup<String> pohlavi = new RadioButtonGroup<>();
        pohlavi.setLabel("Pohlaví");
        pohlavi.setItems("Muž", "Žena", "Jiné");
        pohlavi.setValue("Muž"); // nastaví výchozí hodnotu
        add(jmeno, prijmeni, pohlavi, email, telefon);

        Button button = new Button("Zaregistrovat");
        button.addClickListener( event -> {
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.setFname(jmeno.getValue());
                    dto.setLname(prijmeni.getValue());
                    String selectedValue = pohlavi.getValue();
                    char sex = selectedValue.charAt(0);
                    dto.setSex(sex);
                    dto.setEmail(email.getValue());
                    dto.setWork_phone(telefon.getValue());

                    dto.setID(null);

                    EmployeeDTO serverResponse = create(dto);

                    Dialog dialog = new Dialog();
                    dialog.setHeaderTitle(String.format("Vaše přihlašovací ID je %s", serverResponse.getID()));
                    Button okButton = new Button("OK");
                    okButton.addClickListener(e ->
                            okButton.getUI().ifPresent(ui ->
                                    ui.navigate("prihlaseni"))
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

    EmployeeDTO create(EmployeeDTO inputRegistrationForm){
        return ClientBuilder.newClient()
                .target(url)
                .path("/zamestnanci")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(inputRegistrationForm, MediaType.APPLICATION_JSON_TYPE), EmployeeDTO.class);
    }
}

