package klienci.controllers;

import BazaDanych.ConnectDataBase;
import faktury.controllers.RowProduct;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import mainMenu.Main;
import produkty.controllers.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.*;

public class ListCustomersMenu {
    @FXML
    TableView tableCustomers;

    @FXML
    TableColumn idCustomerTable;

    @FXML
    TableColumn nameCompanyTable;

    @FXML
    TableColumn nipTable;

    @FXML
    TableColumn streetTable;

    @FXML
    TableColumn numberOfStreetTable;

    @FXML
    TableColumn zipCodeTable;

    @FXML
    TableColumn townTable;

    @FXML
    TableColumn mobilePhoneTable;

    @FXML
    private BorderPane listCustomersMenu;

    private List<Customer> listCustomers;

    @FXML
    private ListView listView;

    @FXML
    private Label info;

    public void initialize() throws IOException, ClassNotFoundException {
        idCustomerTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCompanyTable.setCellValueFactory(new PropertyValueFactory<>("nameCompany"));
        nipTable.setCellValueFactory(new PropertyValueFactory<>("nip"));
        streetTable.setCellValueFactory(new PropertyValueFactory<>("streetCompany"));
        numberOfStreetTable.setCellValueFactory(new PropertyValueFactory<>("numberOfStreetCompany"));
        zipCodeTable.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        townTable.setCellValueFactory(new PropertyValueFactory<>("town"));
        mobilePhoneTable.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        tableCustomers.setPlaceholder(new Label("Brak dodanych klientów"));
        tableCustomers.setEditable(true); //do edytowania

        nameCompanyTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        nipTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        streetTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        numberOfStreetTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        zipCodeTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        townTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        mobilePhoneTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania

        listCustomers = new ArrayList<>();
        ConnectDataBase con = new ConnectDataBase();
        listCustomers = con.selectCustomers();
        for(Customer c : listCustomers){
            tableCustomers.getItems().add(c);
        }
    }

    //zapisanie zmian w tabeli
    public void saveCustomers(){
        Customer customer;
        listCustomers = new LinkedList<>();
        ConnectDataBase con = new ConnectDataBase();
        listCustomers = con.selectCustomers();

        Collections.sort(tableCustomers.getItems()); //sortujemy w razie co
        Collections.sort(listCustomers); //sortujemy w razie co

        for(int i=0;i<tableCustomers.getItems().size();i++){
        customer = (Customer) tableCustomers.getItems().get(i);
        if(!customer.getNameCompany().equals(listCustomers.get(i).getNameCompany()) ||
                !customer.getNip().equals(listCustomers.get(i).getNip()) ||
                !customer.getStreetCompany().equals(listCustomers.get(i).getStreetCompany())||
                !customer.getNumberOfStreetCompany().equals(listCustomers.get(i).getNumberOfStreetCompany())||
                !customer.getZipCode().equals(listCustomers.get(i).getZipCode())||
                !customer.getTown().equals(listCustomers.get(i).getTown())||
                !customer.getMobilePhone().equals(listCustomers.get(i).getMobilePhone())||
                !customer.getId().equals(listCustomers.get(i).getId()))
        {
                if(con.editCustomer(customer, listCustomers.get(i).getId())){
                    info.setTextFill(Color.web("#32CD32"));
                    info.setText("Zmiany zostały wprowadzone");
                }else{
                    info.setTextFill(Color.web("#FF0000"));
                    info.setText("Wystąpił błąd!");
                }
        }
    }
        tableCustomers.getItems().clear();
        listCustomers = con.selectCustomers();
        for(Customer c : listCustomers){
            tableCustomers.getItems().add(c);
        }
    }



    //edytowanie id produktu w tabeli
    public void editNameCompanyOfTable(TableColumn.CellEditEvent<Customer, String> nameCompany){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        //sprawdzamy czy pole nazwy są puste
        if(nameCompany.getNewValue().isEmpty() || nameCompany.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wprowadzić nazwę firmy!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setNameCompany(nameCompany.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość
            customer.setNameCompany(nameCompany.getNewValue());
        }
    }


    //edycja nipu
    public void editNipOfTable(TableColumn.CellEditEvent<Customer, String> nip){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        ConnectDataBase con = new ConnectDataBase();
        //sprawdzamy czy pole nazwy są puste
        if(nip.getNewValue().isEmpty() || nip.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wprowadzić NIP!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setNip(nip.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość

            if(!nip.getNewValue().matches("[0-9]{3}-([0-9]{3}-[0-9]{2}|[0-9]{2}-[0-9]{3})-[0-9]{2}")){
                info.setText("Format NIP np. 111-11-111-11 lub 111-111-11-11 !");
                info.setTextFill(Color.web("#FF0000"));
                customer.setNip(nip.getOldValue()); //ustawiamy starą nazwe firmy
                //wstawiamy wartości do tabeli jakie były wcześniej
                boolean clear=false;
                Customer row;
                TableView helpTable = new TableView();
                tableCustomers.getItems().addAll(tableCustomers);
                helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
                for(int i=0;i<helpTable.getItems().size();i++){
                    if(clear==false){
                        tableCustomers.getItems().clear();
                        clear=true;
                    }
                    row = (Customer) helpTable.getItems().get(i);
                    tableCustomers.getItems().add(row);
                }
            }else {
                if(con.nipExist(nip.getNewValue())){
                    info.setText("Taki NIP istnieje już w bazie!");
                    info.setTextFill(Color.web("#FF0000"));
                    customer.setNip(nip.getOldValue()); //ustawiamy starą nazwe firmy
                    //wstawiamy wartości do tabeli jakie były wcześniej
                    boolean clear=false;
                    Customer row;
                    TableView helpTable = new TableView();
                    tableCustomers.getItems().addAll(tableCustomers);
                    helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
                    for(int i=0;i<helpTable.getItems().size();i++){
                        if(clear==false){
                            tableCustomers.getItems().clear();
                            clear=true;
                        }
                        row = (Customer) helpTable.getItems().get(i);
                        tableCustomers.getItems().add(row);
                    }
                }else {
                    customer.setNip(nip.getNewValue());
                }
            }
        }
    }

    //edycja ulicy

    public void editStreetOfTable(TableColumn.CellEditEvent<Customer, String> street){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        //sprawdzamy czy pole nazwy są puste
        if(street.getNewValue().isEmpty() || street.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wpisać ulicę!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setStreetCompany(street.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość
            customer.setStreetCompany(street.getNewValue());
        }
    }

    public void editNumberStreetOfTable(TableColumn.CellEditEvent<Customer, String> number){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        //sprawdzamy czy pole nazwy są puste
        if(number.getNewValue().isEmpty() || number.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wpisać numer ulicy!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setNumberOfStreetCompany(number.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość
            customer.setNumberOfStreetCompany(number.getNewValue());
        }
    }

    public void editZipCodeOfTable(TableColumn.CellEditEvent<Customer, String> zipCode){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        //sprawdzamy czy pole nazwy są puste
        if(zipCode.getNewValue().isEmpty() || zipCode.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wpisać kod pocztowy!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setZipCode(zipCode.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{
            if(!zipCode.getNewValue().matches("[0-9]{2}-[0-9]{3}")){
                info.setText("Format kodu pocztowego np. 12-234");
                info.setTextFill(Color.web("#FF0000"));
                customer.setZipCode(zipCode.getOldValue()); //ustawiamy starą nazwe firmy
                //wstawiamy wartości do tabeli jakie były wcześniej
                boolean clear=false;
                Customer row;
                TableView helpTable = new TableView();
                tableCustomers.getItems().addAll(tableCustomers);
                helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
                for(int i=0;i<helpTable.getItems().size();i++){
                    if(clear==false){
                        tableCustomers.getItems().clear();
                        clear=true;
                    }
                    row = (Customer) helpTable.getItems().get(i);
                    tableCustomers.getItems().add(row);
                }
            }else {
                //jeśli nie jest puste pole nazwy to zmień na nową wartość
                customer.setZipCode(zipCode.getNewValue());
            }
        }
    }

    public void editTownOfTable(TableColumn.CellEditEvent<Customer, String> town){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        //sprawdzamy czy pole nazwy są puste
        if(town.getNewValue().isEmpty() || town.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wpisać miasto!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setTown(town.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{
            if(!town.getNewValue().matches("[A-ZĄĘŁŃÓŚŹŻ][a-ząćęłńóśźżA-ZĄĘŁŃÓŚŹŻ\\-\\s]*")){
                info.setText("Miasto zaczyna się z dużej litery i posiada tylko litery!");
                info.setTextFill(Color.web("#FF0000"));
                customer.setTown(town.getOldValue()); //ustawiamy starą nazwe firmy
                //wstawiamy wartości do tabeli jakie były wcześniej
                boolean clear=false;
                Customer row;
                TableView helpTable = new TableView();
                tableCustomers.getItems().addAll(tableCustomers);
                helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
                for(int i=0;i<helpTable.getItems().size();i++){
                    if(clear==false){
                        tableCustomers.getItems().clear();
                        clear=true;
                    }
                    row = (Customer) helpTable.getItems().get(i);
                    tableCustomers.getItems().add(row);
                }
            }else {
                //jeśli nie jest puste pole nazwy to zmień na nową wartość
                customer.setTown(town.getNewValue());
            }
        }
    }

    public void editMobilePhoneOfTable(TableColumn.CellEditEvent<Customer, String> mobilePhone){
        info.setText(null);
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();
        //sprawdzamy czy pole nazwy są puste
        if(mobilePhone.getNewValue().isEmpty() || mobilePhone.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wpisać miasto!");
            info.setTextFill(Color.web("#FF0000"));
            customer.setMobilePhone(mobilePhone.getOldValue()); //ustawiamy starą nazwe firmy
            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Customer row;
            TableView helpTable = new TableView();
            tableCustomers.getItems().addAll(tableCustomers);
            helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableCustomers.getItems().clear();
                    clear=true;
                }
                row = (Customer) helpTable.getItems().get(i);
                tableCustomers.getItems().add(row);
            }
        }else{

            if(!mobilePhone.getNewValue().matches("[0-9]{9}")){
                info.setText("Numer telefonu składa się z 9 cyfr!");
                info.setTextFill(Color.web("#FF0000"));
                customer.setMobilePhone(mobilePhone.getOldValue()); //ustawiamy starą nazwe firmy
                //wstawiamy wartości do tabeli jakie były wcześniej
                boolean clear=false;
                Customer row;
                TableView helpTable = new TableView();
                tableCustomers.getItems().addAll(tableCustomers);
                helpTable.getItems().addAll(tableCustomers.getItems()); //przechwoujemy
                for(int i=0;i<helpTable.getItems().size();i++){
                    if(clear==false){
                        tableCustomers.getItems().clear();
                        clear=true;
                    }
                    row = (Customer) helpTable.getItems().get(i);
                    tableCustomers.getItems().add(row);
                }
            }else {
                //jeśli nie jest puste pole nazwy to zmień na nową wartość
                customer.setMobilePhone(mobilePhone.getNewValue());
            }
        }
    }

    public void returnToCustomersMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/klienci/fxmls/customersmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(listCustomersMenu);
        listCustomersMenu.setCenter(mainmenu);
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }

}
