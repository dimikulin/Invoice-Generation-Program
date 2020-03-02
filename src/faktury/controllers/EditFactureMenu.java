package faktury.controllers;

import BazaDanych.ConnectDataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import mainMenu.Main;
import org.w3c.dom.Text;
import produkty.controllers.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EditFactureMenu {

    @FXML
    Label labelInfo;


    @FXML
    BorderPane addFactureMenu;

    @FXML
    TableView tableProducts;

    @FXML
    TableColumn idProductTable;

    @FXML
    TableColumn amountProduct;

    @FXML
    TextField idProduct;

    @FXML
    TextField amount;

    @FXML
    Button addProduct;

    @FXML
    Button addFacture;

    @FXML
    Label labelIdCompany;

    @FXML
    Label labelDateFacture;

    List<RowProduct> listProducts;

    //funkcja uruchamiająca się w momencie wyswietlenia panelu
    public void initialize(){

        labelIdCompany.setText(labelIdCompany.getText()+ListFacturesMenu.getIdEditFactureToTransfer());//pobranie id edytowanej faktury
        labelDateFacture.setText(labelDateFacture.getText()+ListFacturesMenu.getDataFactureToTransfer());//pobranie daty edytowanej faktury

        addProduct.setDisable(true); //ustawienie braku widocznosci przycisku dodania produktu


        idProductTable.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        amountProduct.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tableProducts.setPlaceholder(new Label("Brak dodanych produktów"));
        tableProducts.setEditable(true); //do edytowania

        idProductTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        amountProduct.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania

        listProducts = new ArrayList<>();
        ConnectDataBase con = new ConnectDataBase();
        listProducts = con.selectProductsOfFactures(ListFacturesMenu.getIdEditFactureToTransfer());
        for(RowProduct f : listProducts){
            tableProducts.getItems().add(f);
        }
    }

    //stan przycisku dodania produktu
    public void stateAddProductButton(){
        boolean disableButton = idProduct.getText().isEmpty() ||
                idProduct.getText().trim().isEmpty() ||
                amount.getText().isEmpty() ||
                amount.getText().trim().isEmpty();
        addProduct.setDisable(disableButton);
    }

    //stan przycisku wstawienia faktury
    public void stateAddFactureButton(){
        boolean disableButton = ListFacturesMenu.getIdEditFactureToTransfer().isEmpty() ||
                ListFacturesMenu.getIdEditFactureToTransfer().trim().isEmpty() ||
                tableProducts.getItems().size()==0;
        addFacture.setDisable(disableButton);
    }

    //dodawanie produktu do tabeli
    public void addProduct(){
        boolean canAddProductToList=true;
        labelInfo.setText(null);
        labelInfo.setTextFill(null);
        labelInfo.setTextFill(Color.web("#FF0000"));
        ConnectDataBase con = new ConnectDataBase();
        RowProduct row;
        if(con.checkIdProduct(idProduct.getText()) && amount.getText().matches("^[1-9][0-9]?+$")){
            for(int i=0;i<tableProducts.getItems().size();i++){
                row = (RowProduct) tableProducts.getItems().get(i);
                if(row.getIdProduct().equals(idProduct.getText())){
                    labelInfo.setText("Produkt o takim id istnieje już na liście faktury");
                    labelInfo.setTextFill(Color.web("#FF0000"));
                    canAddProductToList=false;
                }
            }
            if(canAddProductToList) {
                RowProduct product = new RowProduct(idProduct.getText(), amount.getText());
                tableProducts.getItems().add(product);
                idProduct.clear();
                amount.clear();
                addProduct.setDisable(true);
            }
        }else if(!con.checkIdProduct(idProduct.getText())){
            labelInfo.setText("Produkt o takim id nie istnieje");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }else{
            labelInfo.setText("Ilość musi być liczbą i nie może zaczynać się liczbą 0");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }
        this.stateAddFactureButton(); //odswiezamy stan przycisku wystawienia faktury
    }

    public void deleteSelectedRow(){
        labelInfo.setText(null);
        tableProducts.getItems().removeAll(tableProducts.getSelectionModel().getSelectedItem());
        this.stateAddFactureButton(); //odswiezamy stan przycisku wystawienia faktury
    }

    //edytowanie id produktu w tabeli
    public void editIdProductofTable(TableColumn.CellEditEvent<RowProduct, String> idProductt) {
        RowProduct row;
        labelInfo.setText(null);
        ConnectDataBase con = new ConnectDataBase();
        RowProduct editRow = (RowProduct) tableProducts.getSelectionModel().getSelectedItem();
        //sprawdzamy czy takie id produktu istnieje w bazie danych
        if (con.checkIdProduct(idProductt.getNewValue())) {
            boolean checkIdProductOnTable = false;

            for (int i = 0; i < tableProducts.getItems().size(); i++) {
                row = (RowProduct) tableProducts.getItems().get(i);
                if (row.getIdProduct().equals(idProductt.getNewValue())) {
                    checkIdProductOnTable = true;
                }
            }
            if (checkIdProductOnTable) {
                boolean clear = false;
                labelInfo.setText("Produkt o takim id istnieje już na liście faktury");
                labelInfo.setTextFill(Color.web("#FF0000"));
                editRow.setIdProduct(idProductt.getOldValue()); //ustawiamy starą wartość
                TableView helpTable = new TableView();
                tableProducts.getItems().addAll(tableProducts);
                helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
                System.out.println("Rozmiar przed " + helpTable.getItems().size());
                for (int i = 0; i < helpTable.getItems().size(); i++) {
                    if (clear == false) {
                        tableProducts.getItems().clear();
                        clear = true;
                    }
                    System.out.println("Rozmiar po " + helpTable.getItems().size());
                    row = (RowProduct) helpTable.getItems().get(i);
                    System.out.println("ID: " + row.getIdProduct());
                    System.out.println("Ilość: " + row.getAmount());
                    tableProducts.getItems().add(row);
                }

            } else {
                editRow.setIdProduct(idProductt.getNewValue());
            }
        } else {
            labelInfo.setText("Produkt o takim id nie istnieje");
            labelInfo.setTextFill(Color.web("#FF0000"));
//            editRow.setIdProduct(idProductt.getOldValue());
//            tableProducts.getItems().addAll(tableProducts);


            boolean clear = false;
            editRow.setIdProduct(idProductt.getOldValue()); //ustawiamy starą wartość
            TableView helpTable = new TableView();
            tableProducts.getItems().addAll(tableProducts);
            helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
            System.out.println("Rozmiar przed " + helpTable.getItems().size());
            for (int i = 0; i < helpTable.getItems().size(); i++) {
                if (clear == false) {
                    tableProducts.getItems().clear();
                    clear = true;
                }
                System.out.println("Rozmiar po " + helpTable.getItems().size());
                row = (RowProduct) helpTable.getItems().get(i);
                System.out.println("ID: " + row.getIdProduct());
                System.out.println("Ilość: " + row.getAmount());
                tableProducts.getItems().add(row);


            }
        }
    }

    //edytowanie ilości produktu w tabeli
    public void editAmountProductofTable(TableColumn.CellEditEvent<RowProduct, String> amount){
        RowProduct editRow = (RowProduct) tableProducts.getSelectionModel().getSelectedItem();
        editRow.setAmount(amount.getNewValue());
    }

    //powrot do menu wczesniejszego
    public void returnToListFacturesMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/faktury/fxmls/listFacturesMenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(addFactureMenu);
        addFactureMenu.setCenter(mainmenu);
    }

    public void addFacture() throws SQLException {
        boolean addSuccess = true;//informuje nas czy faktura razem z produktami dodała się do bazy poprawnie
        ConnectDataBase con = new ConnectDataBase();
        if (con.checkIdCustomer(ListFacturesMenu.getIdEditCustomerToTransfer()) && tableProducts.getItems().size()!=0) {

            double priceGross = 0;
            double priceNet = 0;
            double price=0;
            double priceGrosss=0;
            String nameProduct;
            String priceProduct;
            List<Product> listProducts = con.selectProducts();
            RowProduct row;
            con.deleteRowsFacture(ListFacturesMenu.getIdEditFactureToTransfer());
            for (int i = 0; i < tableProducts.getItems().size(); i++) {
                row = (RowProduct) tableProducts.getItems().get(i);
                nameProduct = listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getNameProduct();
                System.out.println("Nazwa Produktu: " + nameProduct);
                priceProduct = listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getPriceNet();
                System.out.println("Cena Produktu: " + priceProduct);
                System.out.println("Ilość produktu+"+row.getAmount());
                priceProduct = listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getVat();
                System.out.println("Vat Produktu: " + priceProduct);
                if (!con.addRowFacture(ListFacturesMenu.getIdEditFactureToTransfer(), String.valueOf(row.getIdProduct()), String.valueOf(row.getAmount()))) {
                    addSuccess = false;
                }

                price = Double.parseDouble(listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getPriceNet()) * Double.parseDouble(row.getAmount());
                price*=100;
                price = Math.round(price);
                price/=100;
                priceNet = priceNet + price;

                priceGrosss = (((Double.parseDouble(listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getPriceNet()) * Double.parseDouble(listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getVat())) / 100) + Double.parseDouble(listProducts.get(Integer.parseInt(row.getIdProduct()) - 1).getPriceNet())) * Integer.parseInt(row.getAmount());
                priceGrosss*=100;
                priceGrosss = Math.round(priceGrosss);
                priceGrosss/=100;
                priceGross = priceGross + priceGrosss;
                System.out.println("Cena netto w tym momencie "+priceNet);
                System.out.println("Cena brutto w tym momencie "+priceGross);
            }


            priceNet*=100;
            priceNet = Math.round(priceNet);
            priceNet/=100;


            priceGross*=100;
            priceGross = Math.round(priceGross);
            priceGross/=100;


            System.out.println("Ogólna cena brutto wynosi: " + priceGross);
            //Dodanie ceny netto i brutto do faktury
            if (!con.updatePriceFacture(ListFacturesMenu.getIdEditFactureToTransfer(), priceNet, priceGross)) {
                addSuccess = false;
            }

            if (!addSuccess) {
                labelInfo.setText("Faktura nie została dodana poprawnie do bazy danych");
                labelInfo.setTextFill(Color.web("#FF0000"));
            } else {
                labelInfo.setText("Zaktualizowano fakturę");
                labelInfo.setTextFill(Color.web("#32CD32"));
            }
        }else if(ListFacturesMenu.getIdEditFactureToTransfer().isEmpty() || ListFacturesMenu.getIdEditFactureToTransfer().trim().isEmpty()){
            labelInfo.setText("Puste pole ID Firmy");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }else if(tableProducts.getItems().size()==0){
            labelInfo.setText("Brak dodanych produktów");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }
        else{
            labelInfo.setText("Firma o takim id nie istnieje w bazie");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }
    }

    //czyszczenie widoku
    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }
}
