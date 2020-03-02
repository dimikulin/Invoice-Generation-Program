package klienci.controllers;

import BazaDanych.ConnectDataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import mainMenu.Main;

import org.w3c.dom.Document;
import javax.xml.bind.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;

public class AddCustomerMenu {
    @FXML
    private BorderPane addCustomerMenu;

    @FXML
    private Button addCustomerButton;

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

    public void initialize(){
        addCustomerButton.setDisable(true);
    }

    //stan przycisku dodania klienta
    public void stateAddCustomerButton(){
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
        addCustomerButton.setDisable(disableButton);
    }

    public void backToCustomerMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/klienci/fxmls/customersmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(addCustomerMenu);
        addCustomerMenu.setCenter(mainmenu);
    }

    //walidacja wprowadzonych danych
    public void checkData() throws JAXBException, ParserConfigurationException, IOException, ClassNotFoundException, SQLException {
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
                if(con.addCustomer(customer)) {
                    labelInfo.setText("Dodano klienta do bazy danych");
                    labelInfo.setTextFill(Color.web("#32CD32"));
                    nameCompany.clear();
                    nip.clear();
                    streetCompany.clear();
                    numberOfStreetCompany.clear();
                    zipCode.clear();
                    town.clear();
                    mobilePhone.clear();
                }else{
                    labelInfo.setText("Nie można dodać klienta do bazy.\nMożliwe, że taki NIP już istnieje w systemie.");
                    labelInfo.setTextFill(Color.web("#FF0000"));
//                    nameCompany.clear();
//                    nip.clear();
//                    streetCompany.clear();
//                    numberOfStreetCompany.clear();
//                    zipCode.clear();
//                    town.clear();
//                    mobilePhone.clear();
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
