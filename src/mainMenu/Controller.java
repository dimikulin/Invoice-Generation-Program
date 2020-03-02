package mainMenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Controller {

    @FXML
    private BorderPane mainmenu;

    public void loadMyCompany() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/mojeDane/fxmls/myCompanyMenu.fxml"));
        BorderPane factures = loader.load();
        clearWindow(mainmenu);
        mainmenu.setCenter(factures);
    }

    public void loadFactures() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/faktury/fxmls/facturesmenu.fxml"));
        BorderPane factures = loader.load();
        clearWindow(mainmenu);
        mainmenu.setCenter(factures);
    }

    public void loadCustomers() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/klienci/fxmls/customersmenu.fxml"));
        BorderPane factures = loader.load();
        clearWindow(mainmenu);
        mainmenu.setCenter(factures);
    }

    public void loadProducts() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/produkty/fxmls/productsmenu.fxml"));
        BorderPane factures = loader.load();
        clearWindow(mainmenu);
        mainmenu.setCenter(factures);
    }

    public void loadExitProgram(){
        Platform.exit();
        System.exit(0);
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
