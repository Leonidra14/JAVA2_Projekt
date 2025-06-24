package project.userInterface;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        ResourceBundle resourceBundle2 = ResourceBundle.getBundle("Texts", new Locale("de"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Texts", new Locale("cs"));
        log.debug(resourceBundle.getString("info"));
        Label nadpis = new Label(resourceBundle2.getString("uvod"));

        nadpis.getElement().getStyle().set("font-size", "60px");
        add(nadpis);

        //vyzkouseni vlakna
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        Thread updateTimeThread = new Thread(() -> {
            while (true) {
                UI ui = this.getUI().orElse(null);
                if (ui != null) {
                    ui.access(() -> {
                        LocalDateTime now = LocalDateTime.now();
                        String formattedDateTime = now.format(formatter);
                        log.info(formattedDateTime);
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Thread interrupted", e);
                }
            }
        });
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();


        //

        Button button2 = new Button("Přihlásit se");
        button2.addClickListener(e ->
                button2.getUI().ifPresent(ui ->
                        ui.navigate("prihlaseni"))
        );
        add(button2);

        Button button = new Button("Registrace");
        button.addClickListener(e ->
                button.getUI().ifPresent(ui ->
                        ui.navigate("registrace"))
        );
        add(button);

        setAlignItems(Alignment.CENTER);
    }
}
