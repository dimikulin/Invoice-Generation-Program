package klienci.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import mainMenu.Main;

import java.io.IOException;

public class Customersmenu {
    @FXML
    private BorderPane customersMenu;

    public void returnToMainMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/mainMenu/mainmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(customersMenu);
        customersMenu.setCenter(mainmenu);
    }
    public void addCustomerMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/klienci/fxmls/addCustomerMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(customersMenu);
        customersMenu.setCenter(mainmenu);
    }

    public void listCustomersMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/klienci/fxmls/listCustomersMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(customersMenu);
        customersMenu.setCenter(mainmenu);
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
