package project.userInterface;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.VaadinService;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.EmployeeDTO;

import javax.servlet.http.Cookie;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Route("prihlaseni")
public class Prihlaseni extends VerticalLayout {
    String url;

    // Konstruktor třídy Prihlaseni s parametrem url, který je nastaven v konfiguračním souboru.
    public Prihlaseni(@Value("${backend.url}") String url){
        this.url = url;

        Label nadpis = new Label("Přihlášení");
        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);

        // Vytvoření textového pole pro zadání hesla
        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Heslo");
        passwordField.setAllowedCharPattern("[0-9]");
        passwordField.setErrorMessage("Neplatné heslo");
        add(passwordField);

        Button button = new Button("OK");
        button.addClickListener( event -> {
            passwordField.getValue();
            boolean flag = false;

                    try {
                        var res = ClientBuilder.newClient()
                                .target(url)
                                .path("/zamestnanci/{id}")
                                .resolveTemplate("id", passwordField.getValue())
                                .request(MediaType.APPLICATION_JSON_TYPE)
                                .get(EmployeeDTO.class);
                    }
                    catch (Exception e){
                        flag = true;
                        Dialog dialog = new Dialog();
                        dialog.setHeaderTitle(String.format("Vaše přihlašovací ID je špatne!"));
                        Button okButton = new Button("OK");
                        okButton.addClickListener(e2 ->
                                dialog.close()
                        );
                        okButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                        dialog.getFooter().add(okButton);

                        dialog.open();
                        add(dialog);
                    }

                    if(flag == false){
                        Cookie myCookie = new Cookie("idEmployee", passwordField.getValue());
                        myCookie.setMaxAge(120);
                        myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
                        VaadinService.getCurrentResponse().addCookie(myCookie);

                        UI.getCurrent().getPage().setLocation("menuZamestnance");
                    }


                }

        );
        add(button);

        setAlignItems(Alignment.CENTER);

    }
}
