package faktury.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import mainMenu.Main;

import java.io.IOException;
import java.nio.file.Paths;

public class Facturesmenu {
    @FXML
    private BorderPane facturesMenu;


    public void showFactures() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/faktury/fxmls/listFacturesMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(facturesMenu);
        facturesMenu.setCenter(mainmenu);
    }

    public void addFactureMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/faktury/fxmls/addFactureMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(facturesMenu);
        facturesMenu.setCenter(mainmenu);
    }

    public void returnToMain() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/mainMenu/mainmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(facturesMenu);
        facturesMenu.setCenter(mainmenu);
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
