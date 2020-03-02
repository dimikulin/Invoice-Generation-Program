package mojeDane.controllers;

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
import java.util.regex.Pattern;

public class MyCompanyMenu {

    @FXML
    BorderPane myCompanyMenu;

    @FXML
    private TextField nameCompany;

    @FXML
    private TextField nip;

    @FXML
    private TextField streetCompany;

    @FXML
    private TextField numberOfStreetCompany;

    @FXML
    private TextField zipCode;

    @FXML
    private TextField town;

    @FXML
    private TextField mobilePhone;

    @FXML
    private Label labelInfo;

    @FXML
    Button editMyCompanyButton;

    public void initialize(){
        ConnectDataBase con = new ConnectDataBase();
        Customer myCompany = con.myCompany();
        nameCompany.setText(myCompany.getNameCompany());
        nip.setText(myCompany.getNip());
        streetCompany.setText(myCompany.getStreetCompany());
        numberOfStreetCompany.setText(myCompany.getNumberOfStreetCompany());
        zipCode.setText(myCompany.getZipCode());
        town.setText(myCompany.getTown());
        mobilePhone.setText(myCompany.getMobilePhone());
        editMyCompanyButton.setDisable(false);
    }

    public void stateEditMyCompanyButton(){
        boolean disableButton = nameCompany.getText().isEmpty() ||
                nameCompany.getText().trim().isEmpty() ||
                nip.getText().isEmpty() ||
                nip.getText().trim().isEmpty() ||
                streetCompany.getText().isEmpty() ||
                streetCompany.getText().trim().isEmpty() ||
                numberOfStreetCompany.getText().isEmpty() ||
                numberOfStreetCompany.getText().trim().isEmpty() ||
                zipCode.getText().isEmpty() ||
                zipCode.getText().trim().isEmpty() ||
                town.getText().isEmpty() ||
                town.getText().trim().isEmpty() ||
                mobilePhone.getText().isEmpty() ||
                mobilePhone.getText().trim().isEmpty();
        editMyCompanyButton.setDisable(disableButton);
    }

    public void checkDate(){
        boolean add=true;
        labelInfo.setText("");

        //sprawdzanie nipu
        if(!Pattern.matches("[0-9]{3}-([0-9]{3}-[0-9]{2}|[0-9]{2}-[0-9]{3})-[0-9]{2}",nip.getText())){
            labelInfo.setText(labelInfo.getText()+"Zły NIP");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }

        //sprawdzanie kodu pocztowego
        if(!Pattern.matches("[0-9]{2}-[0-9]{3}",zipCode.getText())){
            labelInfo.setText(labelInfo.getText()+"\n"+"Zły kod pocztowy");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }

        //sprawdzanie miasta
        if(!Pattern.matches("[A-ZĄĘŁŃÓŚŹŻ][a-ząćęłńóśźżA-ZĄĘŁŃÓŚŹŻ\\-\\s]*",town.getText())){
            labelInfo.setText(labelInfo.getText()+"\n"+"Zły format miejscowości");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }

        //srawdzanie numeru telefonu
        if(!Pattern.matches("[0-9]{9}",mobilePhone.getText())){
            labelInfo.setText(labelInfo.getText()+"\n"+"Zły numer telefonu");
            labelInfo.setTextFill(Color.web("#FF0000"));
            add=false;
        }

        //jeśli dane zostały wpisane poprawnie
        if(add){

            Customer customer = new Customer("1",nameCompany.getText(),nip.getText(),streetCompany.getText(),numberOfStreetCompany.getText(),zipCode.getText(),town.getText(),mobilePhone.getText());
            ConnectDataBase con = new ConnectDataBase();
            if(con.editMyCompany(customer)) {
                labelInfo.setText("Zaktualizowano dane firmy");
                labelInfo.setTextFill(Color.web("#32CD32"));
            }else{
                labelInfo.setText("Nie można zaktualizować danych.\nMożliwe, że taki NIP już istnieje w systemie.");
                labelInfo.setTextFill(Color.web("#FF0000"));

            }

        }
    }

    public void backToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/mainMenu/mainmenu.fxml"));
        BorderPane factures = loader.load();
        clearWindow(myCompanyMenu);
        myCompanyMenu.setCenter(factures);
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
