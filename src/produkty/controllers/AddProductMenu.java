package produkty.controllers;

import BazaDanych.ConnectDataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import klienci.controllers.Customer;
import mainMenu.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddProductMenu {
    @FXML
    private BorderPane addProductMenu;

    @FXML
    private Button addProductButton;

    @FXML
    private TextField nameProduct;

    @FXML
    private TextField netPrice;

    @FXML
    private TextField vat;

    @FXML
    private Label labelInfo;

    public void initialize(){
        addProductButton.setDisable(true);
    }

    //stan przycisku dodania klienta
    public void stateAddProductButton(){
        boolean disableButton = nameProduct.getText().isEmpty() ||
                nameProduct.getText().trim().isEmpty() ||
                netPrice.getText().isEmpty() ||
                netPrice.getText().trim().isEmpty() ||
                vat.getText().isEmpty() ||
                vat.getText().trim().isEmpty();

        addProductButton.setDisable(disableButton);
    }

    public void backToProductMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/produkty/fxmls/productsmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(addProductMenu);
        addProductMenu.setCenter(mainmenu);
    }

    public void checkData() throws SQLException {
        boolean add=true;
        labelInfo.setText("");

        //sprawdzenie czy została podana nazwa produktu (wiem że jest to zrobione ale w razie co :D)
        if(nameProduct.getText().isEmpty() || nameProduct.getText().trim().isEmpty()){
            labelInfo.setText(labelInfo.getText()+"Podaj nazwę produktu\n\n");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }
        //sprawdzanie ceny netto
        if(!Pattern.matches("^([1-9][0-9]*)|(([1-9][0-9]*)\\.([0-9]{1}([0-9]{1})?))|(([0])\\.([0-9]{1}([0-9]{1})?))$",netPrice.getText())){
            labelInfo.setText(labelInfo.getText()+"Niepoprawna cena. Nie może zaczynać się liczbą 0 \ni po kropce mogą wystąpić maks. 2 liczby\n\n");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }
        //sprawdzanie procent vat
        if(!Pattern.matches("^([1-9][0-9]*)|(([1-9][0-9]*)\\.([0-9]{1}([0-9]{1})?))|(([0])\\.([0-9]{1}([0-9]{1})?))$",vat.getText())){
            labelInfo.setText(labelInfo.getText()+"Niepoprawna forma podatku. Nie może zaczynać się\nliczbą 0 i po kropce mogą wystąpić maks. 2 liczby");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }

        if(add){
           Product product= new Product("1",nameProduct.getText(),netPrice.getText(),vat.getText());
            ConnectDataBase con = new ConnectDataBase();
            if(con.addProduct(product)) {
                labelInfo.setText("Dodano produkt do bazy danych");
                labelInfo.setTextFill(Color.web("#32CD32"));
                nameProduct.clear();
                netPrice.clear();
                vat.clear();
            }else{
                labelInfo.setText("Nie można dodać productu do bazy.");
                labelInfo.setTextFill(Color.web("#FF0000"));
                nameProduct.clear();
                netPrice.clear();
                vat.clear();
            }
        }
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
