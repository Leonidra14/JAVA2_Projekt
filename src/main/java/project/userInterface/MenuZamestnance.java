package project.userInterface;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import project.business.dto.ProjectDTO;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.servlet.http.Cookie;
import java.util.Currency;
import java.util.Date;
@Log4j2
@Route("menuZamestnance")
public class MenuZamestnance extends VerticalLayout implements BeforeEnterObserver {
    String url;

    public MenuZamestnance(@Value("${backend.url}") String url){
        this.url = url;
        addCookie();

        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED);

        MenuItem pridat = menuBar.addItem("Přidat");
        MenuItem adresaTabulka = menuBar.addItem("Zobrazit tabulku adres");
        MenuItem projektTabulka = menuBar.addItem("Zobrazit tabulku projektu");
        MenuItem oddeleniTabulka = menuBar.addItem("Zobrazit tabulku oddeleni");
        SubMenu shareSubMenu = pridat.getSubMenu();
        MenuItem vytvoritOddeleni = shareSubMenu.addItem("Vytvorit Oddeleni");
        MenuItem vytvoritAdresu = shareSubMenu.addItem("Vytvorit Adresu");
        MenuItem vytvoritProjekt = shareSubMenu.addItem("Vytvorit Projekt");

        adresaTabulka.addClickListener(event -> navigateTo("tabulkaAdresy"));
        projektTabulka.addClickListener(event -> navigateTo("tabulkaProjekty"));
        oddeleniTabulka.addClickListener(event -> navigateTo("tabulkaOddeleni"));
        vytvoritProjekt.addClickListener(event -> navigateTo("vytvoritProjekt"));
        vytvoritAdresu.addClickListener(event -> navigateTo("vytvoritAdresu"));
        vytvoritOddeleni.addClickListener(event -> navigateTo("vytvoritOddeleni"));

        add(menuBar);
        setAlignItems(Alignment.CENTER);



    }

    private void navigateTo(String route) {
        UI.getCurrent().navigate(route);
    }

    private Cookie getCookieByName(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

    private void addCookie(){
        Cookie cookie = getCookieByName("idStatniUrednik");
        if(cookie == null)
            return;

        Cookie myCookie = new Cookie("idStatniUrednik", cookie.getValue());
        myCookie.setMaxAge(120);
        myCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        VaadinService.getCurrentResponse().addCookie(myCookie);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (getCookieByName("idEmployee") == null) {
            event.forwardTo(MainView.class);
        }
    }
}
