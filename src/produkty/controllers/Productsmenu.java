package produkty.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import mainMenu.Main;

import java.io.IOException;

public class Productsmenu {
    @FXML
    private BorderPane productsMenu;

    public void returnToMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/mainMenu/mainmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(productsMenu);
        productsMenu.setCenter(mainmenu);
    }

    public void addProductMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/produkty/fxmls/addProductMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(productsMenu);
        productsMenu.setCenter(mainmenu);
    }

    public void listProductsMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/produkty/fxmls/listProductsMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(productsMenu);
        productsMenu.setCenter(mainmenu);
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
