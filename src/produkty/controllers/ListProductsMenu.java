package produkty.controllers;

import BazaDanych.ConnectDataBase;
import faktury.controllers.ListFacturesMenu;
import faktury.controllers.RowProduct;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import klienci.controllers.Customer;
import mainMenu.Main;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListProductsMenu {

    @FXML
    private BorderPane listProductsMenu;

    private List<Product> listProducts;

    @FXML
    private ListView listView;

    @FXML
    private TableView tableProducts;

    @FXML
    private TableColumn idProductTable;

    @FXML
    private TableColumn nameProductTable;

    @FXML
    private TableColumn priceNet;

    @FXML
    private TableColumn vat;

    @FXML
    private Label info;

    public void initialize() throws IOException, ClassNotFoundException {
        idProductTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameProductTable.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        priceNet.setCellValueFactory(new PropertyValueFactory<>("priceNet"));
        vat.setCellValueFactory(new PropertyValueFactory<>("vat"));
        tableProducts.setPlaceholder(new Label("Brak dodanych produktów"));
        tableProducts.setEditable(true); //do edytowania

        nameProductTable.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        priceNet.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania
        vat.setCellFactory(TextFieldTableCell.forTableColumn()); //do edytowania

        listProducts = new ArrayList<>();
        ConnectDataBase con = new ConnectDataBase();
        listProducts = con.selectProducts();
        for(Product p : listProducts){
            tableProducts.getItems().add(p);
        }
    }

    public void returnToProductsMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/produkty/fxmls/productsmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(listProductsMenu);
        listProductsMenu.setCenter(mainmenu);
    }


   //edycja nazwy produktu
    public void editNameProductOfTable(TableColumn.CellEditEvent<Product, String> nameProduct){
        info.setText(null);
        Product product = (Product) tableProducts.getSelectionModel().getSelectedItem();
        ConnectDataBase con = new ConnectDataBase();
        //sprawdzamy czy pole nazwy jest puste
        if(nameProduct.getNewValue().isEmpty() || nameProduct.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wprowadzić nazwę produktu!");
            info.setTextFill(Color.web("#FF0000"));
            product.setNameProduct(nameProduct.getOldValue()); //ustawiamy starą wartość produktu

            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Product row;
            TableView helpTable = new TableView();
            tableProducts.getItems().addAll(tableProducts);
            helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableProducts.getItems().clear();
                    clear=true;
                }
                row = (Product) helpTable.getItems().get(i);
                tableProducts.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość
            product.setNameProduct(nameProduct.getNewValue());
        }


    }

    public void editPriceNetOfTable(TableColumn.CellEditEvent<Product, String> priceProduct){
        info.setText(null);
        Product product = (Product) tableProducts.getSelectionModel().getSelectedItem();
        ConnectDataBase con = new ConnectDataBase();
        //sprawdzamy czy pole nazwy jest puste
        if(priceProduct.getNewValue().isEmpty() || priceProduct.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wprowadzić cenę produktu!");
            info.setTextFill(Color.web("#FF0000"));
            product.setPriceNet(priceProduct.getOldValue()); //ustawiamy starą wartość produktu

            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Product row;
            TableView helpTable = new TableView();
            tableProducts.getItems().addAll(tableProducts);
            helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableProducts.getItems().clear();
                    clear=true;
                }
                row = (Product) helpTable.getItems().get(i);
                tableProducts.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość
            //jeśli to nie jest liczba
            if(!priceProduct.getNewValue().matches("^([1-9][0-9]*)|(([1-9][0-9]*)\\.([0-9]{1}([0-9]{1})?))|(([0])\\.([0-9]{1}([0-9]{1})?))$")){
                info.setText("Proszę wprowadzić poprawny format ceny");
                info.setTextFill(Color.web("#FF0000"));
                product.setPriceNet(priceProduct.getOldValue()); //ustawiamy starą wartość produktu

                //wstawiamy wartości do tabeli jakie były wcześniej
                boolean clear=false;
                Product row;
                TableView helpTable = new TableView();
                tableProducts.getItems().addAll(tableProducts);
                helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
                for(int i=0;i<helpTable.getItems().size();i++){
                    if(clear==false){
                        tableProducts.getItems().clear();
                        clear=true;
                    }
                    row = (Product) helpTable.getItems().get(i);
                    tableProducts.getItems().add(row);
                }
            }else {
                //ustaw nową cenę
                product.setPriceNet(priceProduct.getNewValue());
            }
        }
    }

    public void editVatOfTable(TableColumn.CellEditEvent<Product, String> vatProduct){
        info.setText(null);
        Product product = (Product) tableProducts.getSelectionModel().getSelectedItem();
        ConnectDataBase con = new ConnectDataBase();
        //sprawdzamy czy pole nazwy jest puste
        if(vatProduct.getNewValue().isEmpty() || vatProduct.getNewValue().trim().isEmpty()) {
            info.setText("Proszę wprowadzić wartość podatku!");
            info.setTextFill(Color.web("#FF0000"));
            product.setVat(vatProduct.getOldValue()); //ustawiamy starą wartość produktu

            //wstawiamy wartości do tabeli jakie były wcześniej
            boolean clear=false;
            Product row;
            TableView helpTable = new TableView();
            tableProducts.getItems().addAll(tableProducts);
            helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
            for(int i=0;i<helpTable.getItems().size();i++){
                if(clear==false){
                    tableProducts.getItems().clear();
                    clear=true;
                }
                row = (Product) helpTable.getItems().get(i);
                tableProducts.getItems().add(row);
            }
        }else{ //jeśli nie jest puste pole nazwy to zmień na nową wartość

            if(!vatProduct.getNewValue().matches("^([1-9][0-9]*)|(([1-9][0-9]*)\\.([0-9]{1}([0-9]{1})?))|(([0])\\.([0-9]{1}([0-9]{1})?))$")){
                info.setText("Proszę wprowadzić poprawny format podatku");
                info.setTextFill(Color.web("#FF0000"));
                product.setVat(vatProduct.getOldValue()); //ustawiamy starą wartość produktu

                //wstawiamy wartości do tabeli jakie były wcześniej
                boolean clear=false;
                Product row;
                TableView helpTable = new TableView();
                tableProducts.getItems().addAll(tableProducts);
                helpTable.getItems().addAll(tableProducts.getItems()); //przechwoujemy
                for(int i=0;i<helpTable.getItems().size();i++){
                    if(clear==false){
                        tableProducts.getItems().clear();
                        clear=true;
                    }
                    row = (Product) helpTable.getItems().get(i);
                    tableProducts.getItems().add(row);
                }
            }else { //ustaw nowy podatek
                product.setVat(vatProduct.getNewValue());
            }
        }
    }

    private void updatePricesOfFactures(String id){
        ConnectDataBase con = new ConnectDataBase();
        List<String> faktury=con.getFacturesWithThisProduct(id);
        List<RowProduct> listRowProductsOfFactures;
        System.out.println("Liczba faktur dla produktu o id "+id+" = "+faktury.size());
        for(int i=0;i<faktury.size();i++){
            System.out.println("Faktura numer "+faktury.get(i));
            listRowProductsOfFactures = con.selectProductsOfFactures(faktury.get(i));
            System.out.println("Liczba pozycji "+listRowProductsOfFactures.size());

            double priceGross = 0;
            double priceNet = 0;
            double price=0;
            double priceGrosss=0;

            for(int k=0;k<listRowProductsOfFactures.size();k++){
                System.out.println("Produkt "+listRowProductsOfFactures.get(k).getIdProduct()+" Ilość "+listRowProductsOfFactures.get(k).getAmount());
                double priceNetOfProduct = Double.parseDouble(con.getPriceNetOfProduct(listRowProductsOfFactures.get(k).getIdProduct()));
                double amountOfProduct = Double.parseDouble(listRowProductsOfFactures.get(k).getAmount());
                double vatOfProduct = Double.parseDouble(con.getVATofProduct(listRowProductsOfFactures.get(k).getIdProduct()));

                System.out.println("Cena= "+ priceNetOfProduct);
                System.out.println("Podatek= "+ vatOfProduct);

                price = priceNetOfProduct * amountOfProduct;
                price*=100;
                price = Math.round(price);
                price/=100;
                priceNet = priceNet + price;

                priceGrosss = ((((priceNetOfProduct * vatOfProduct) / 100) + priceNetOfProduct) * amountOfProduct);
                priceGrosss*=100;
                priceGrosss = Math.round(priceGrosss);
                priceGrosss/=100;
                priceGross = priceGross + priceGrosss;

            }
            priceNet*=100;
            priceNet = Math.round(priceNet);
            priceNet/=100;


            priceGross*=100;
            priceGross = Math.round(priceGross);
            priceGross/=100;

            System.out.println("Cena netto zaktualizowana: "+priceNet);
            System.out.println("Cena brutto zaktualizowana: "+priceGross);
            con.updatePriceFacture(faktury.get(i), priceNet, priceGross);//zaktualizowanie cen faktur gdzie był produkt w którym zmienilismy cene lub podatek
            listRowProductsOfFactures.clear();
        }
    }

    public void saveProducts() {
        info.setText(null);
        Product product;
        listProducts = new LinkedList<>();
        ConnectDataBase con = new ConnectDataBase();
        listProducts = con.selectProducts();

        Collections.sort(tableProducts.getItems()); //sortujemy w razie co
        Collections.sort(listProducts); //sortujemy w razie co

        for (int i = 0; i < tableProducts.getItems().size(); i++) {
            product = (Product) tableProducts.getItems().get(i);
            if (!product.getNameProduct().equals(listProducts.get(i).getNameProduct()) ||
                    !product.getPriceNet().equals(listProducts.get(i).getPriceNet()) ||
                    !product.getVat().equals(listProducts.get(i).getVat()) ||
                    !product.getId().equals(listProducts.get(i).getId())) {
                if (con.editProduct(product, listProducts.get(i).getId())) {
                    //sprawwdzamy
                    //updatePricesOfFactures(listProducts.get(i).getId());


                    info.setTextFill(Color.web("#32CD32"));
                    info.setText("Zmiany zostały wprowadzone");
                } else {
                    info.setTextFill(Color.web("#FF0000"));
                    info.setText("Wystąpił błąd!");
                }
            }
        }
        tableProducts.getItems().clear();
        listProducts = con.selectProducts();
        for(Product p : listProducts){
            tableProducts.getItems().add(p);
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
