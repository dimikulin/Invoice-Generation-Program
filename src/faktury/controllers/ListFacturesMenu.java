package faktury.controllers;

import BazaDanych.ConnectDataBase;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import klienci.controllers.Customer;
import mainMenu.Main;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListFacturesMenu extends Application {

    BaseFont helvetica;

    private static String idEditFactureToTransfer;
    private static String idEditCustomerToTransfer;
    private static String editDateToTransfer;

    @FXML
    BorderPane listFacturesMenu;

    @FXML
    TableView tableFactures;

    @FXML
    TableColumn idFactureTable;

    @FXML
    TableColumn nameCompanyTable;

    @FXML
    TableColumn dateTable;

    @FXML
    TableColumn priceNetTable;

    @FXML
    TableColumn priceGrossTable;

    @FXML
    Label labelInfo;

    @FXML
    Button editFactureButton;

    @FXML
    Button deleteFactureButton;

    @FXML
    Button createFactureButton;

    @FXML
    TextField idEditFacture;

    @FXML
    TextField idDeleteFacture;

    @FXML
    TextField idCreateFacture;

    List<Facture> listFactures;

    public void initialize() throws IOException, ClassNotFoundException {
        idFactureTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCompanyTable.setCellValueFactory(new PropertyValueFactory<>("nameCompany"));
        dateTable.setCellValueFactory(new PropertyValueFactory<>("data"));
        priceNetTable.setCellValueFactory(new PropertyValueFactory<>("priceNet"));
        priceGrossTable.setCellValueFactory(new PropertyValueFactory<>("priceGross"));
        tableFactures.setPlaceholder(new Label("Brak wystawionych faktur"));

        editFactureButton.setDisable(true);
        deleteFactureButton.setDisable(true);
        createFactureButton.setDisable(true);

        listFactures = new ArrayList<>();
        ConnectDataBase con = new ConnectDataBase();
        listFactures = con.selectFactures();
        for(Facture f : listFactures){
            tableFactures.getItems().add(f);
        }
    }

    public void returnToFacturesMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/faktury/fxmls/facturesmenu.fxml"));
        BorderPane mainmenu = loader.load();
        clearWindow(listFacturesMenu);
        listFacturesMenu.setCenter(mainmenu);
    }

    public void deleteFacture() throws SQLException {
        labelInfo.setText(null);
        ConnectDataBase con = new ConnectDataBase();
        if(con.checkIdFacture(idDeleteFacture.getText())){
            con.deleteFacture(idDeleteFacture.getText());
            labelInfo.setText("Faktura została usunięta");
            labelInfo.setTextFill(Color.web("#32CD32"));
            tableFactures.getItems().clear();//czyszczenie tabeli
            //zaktualizaowanie tabeli
            listFactures = con.selectFactures();
            for(Facture f : listFactures){
                tableFactures.getItems().add(f);
            }
        }else{
            labelInfo.setText("Nie ma faktury o takim id");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }
        idDeleteFacture.clear();
        deleteFactureButton.setDisable(true);
    }


    private  PdfPCell sprzedawcaInfo(Customer sprzedawca) throws IOException, DocumentException {
        helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        com.itextpdf.text.Font polskieFonty=new com.itextpdf.text.Font(helvetica,10);
        PdfPCell sprzedawcaInfo = new PdfPCell(new Paragraph("Sprzedawca: "+sprzedawca.getNameCompany()+"\n"+
                "ul. "+sprzedawca.getStreetCompany()+" "+sprzedawca.getNumberOfStreetCompany()+"\n"+
                sprzedawca.getZipCode()+" "+sprzedawca.getTown()+"\n"+
                sprzedawca.getNip()+"\n"+
                "tel.: "+sprzedawca.getMobilePhone(),polskieFonty));
        sprzedawcaInfo.setBorder(Rectangle.NO_BORDER);
        sprzedawcaInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        return sprzedawcaInfo;
    }

    private PdfPCell nabywcaInfo(Customer klient) throws IOException, DocumentException {
        helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        com.itextpdf.text.Font polskieFonty=new com.itextpdf.text.Font(helvetica,10);
        PdfPCell sprzedawcaInfo = new PdfPCell(new Paragraph("Nabywca: "+klient.getNameCompany()+"\n"+
                "ul. "+klient.getStreetCompany()+" "+klient.getNumberOfStreetCompany()+"\n"+
                klient.getZipCode()+" "+klient.getTown()+"\n"+
                klient.getNip()+"\n"+
                "tel.: "+klient.getMobilePhone(),polskieFonty));
        sprzedawcaInfo.setBorder(Rectangle.NO_BORDER);
        sprzedawcaInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
        return sprzedawcaInfo;
    }

    public PdfPTable podpis() throws IOException, DocumentException {
        helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        com.itextpdf.text.Font polskieFonty=new com.itextpdf.text.Font(helvetica,8);
        PdfPTable table = new PdfPTable(2); // 2 columns.
        PdfPCell nabywca = new PdfPCell(new Paragraph("............................................................."+"\n"+"Osoba upoważniona do odbioru",polskieFonty));
        PdfPCell sprzedawca = new PdfPCell(new Paragraph(".........................................................."+"\n"+"Osoba upoważniona do wystawienia",polskieFonty));

        nabywca.setBorder(Rectangle.NO_BORDER);
        nabywca.setHorizontalAlignment(Element.ALIGN_CENTER);
        sprzedawca.setBorder(Rectangle.NO_BORDER);
        sprzedawca.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(nabywca);
        table.addCell(sprzedawca);
        return table;
    }

    public void createFacturePDF() throws IOException, DocumentException {
        labelInfo.setText(null);
        ConnectDataBase con = new ConnectDataBase();
        if(con.checkIdFacture(idCreateFacture.getText())){
            //TWORZENIE DOKUMENTU FAKTURY PDF

            Document document = new Document();
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            com.itextpdf.text.Font polskieFonty=new com.itextpdf.text.Font(helvetica,10);
            PdfWriter.getInstance(document, new FileOutputStream("F"+idCreateFacture.getText()+".pdf")); //nazwa pliku faktury
            document.open();
            Paragraph tytul = new Paragraph("Faktura numer "+idCreateFacture.getText());
            tytul.setAlignment(Element.ALIGN_CENTER);
            document.add(tytul);
            document.add(new Paragraph("\n"));
            Paragraph dataInfo = new Paragraph("Wystawiona dnia "+con.getDateFacture(idCreateFacture.getText()),polskieFonty);
            dataInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(dataInfo);
            document.add(new Paragraph("\n"));

            //Informacje dotyczące sprzedawcy i nabywcy
            PdfPTable tablica2 = new PdfPTable(2); // 2 columns.
            System.out.println(con.myCompany().getNameCompany());
            tablica2.addCell(sprzedawcaInfo(con.myCompany()));
            tablica2.addCell(nabywcaInfo(con.getCustomer(con.checkIdCustomerOfFacture(idCreateFacture.getText()))));
            document.add(tablica2);

            PdfPTable table = new PdfPTable(7); // 7 columns.
            document.add(new Paragraph("\n"));
            PdfPCell cell1 = new PdfPCell(new Paragraph("Produkt",polskieFonty));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Ilość",polskieFonty));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Cena netto[zł]",polskieFonty));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Wartość netto[zł]",polskieFonty));
            PdfPCell cell5 = new PdfPCell(new Paragraph("VAT[%]",polskieFonty));
            PdfPCell cell6 = new PdfPCell(new Paragraph("Podatek VAT[zł]",polskieFonty));
            PdfPCell cell7 = new PdfPCell(new Paragraph("Wartość brutto[zł]",polskieFonty));
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);

            List<String> listProducts = con.showProductsOfFacture(idCreateFacture.getText());
            List<RowProduct> rowsFacture = con.selectProductsOfFactures(idCreateFacture.getText());

            double priceGross=0;
            double priceGrossOfProducts=0;
            double priceNetOfProducts=0;
            for(int i=0;i<listProducts.size();i++)
            {

                int amount = Integer.parseInt(rowsFacture.get(i).getAmount());//ilość produktu
                double priceNetOfProduct = Double.parseDouble(con.getPriceNetOfProduct(rowsFacture.get(i).getIdProduct()));


                double priceNet = amount*priceNetOfProduct;
                double duty = Double.parseDouble(con.getVATofProduct(rowsFacture.get(i).getIdProduct()));

                priceNet*=100;
                priceNet=Math.round(priceNet);
                priceNet/=100;

                priceNetOfProducts+=priceNet;
                priceNetOfProducts*=100;
                priceNetOfProducts=Math.round( priceNetOfProducts);
                priceNetOfProducts/=100;

                double dutyPrice=(priceNet*duty)/100;

                dutyPrice*=100;
                dutyPrice=Math.round(dutyPrice);
                dutyPrice/=100;

                priceGrossOfProducts+=dutyPrice;
                priceGrossOfProducts*=100;
                priceGrossOfProducts=Math.round(priceGrossOfProducts);
                priceGrossOfProducts/=100;

                double priceGrossOfProduct=dutyPrice+priceNet;
                priceGrossOfProduct*=100;
                priceGrossOfProduct=Math.round(priceGrossOfProduct);
                priceGrossOfProduct/=100;



                priceGross+=priceGrossOfProduct;
                priceGross*=100;
                priceGross=Math.round(priceGross);
                priceGross/=100;

                PdfPCell cell11 = new PdfPCell(new Paragraph(listProducts.get(i),polskieFonty));
                table.addCell(cell11);
                PdfPCell cell12 = new PdfPCell(new Paragraph(String.valueOf(amount),polskieFonty));
                table.addCell(cell12);
                PdfPCell cell13 = new PdfPCell(new Paragraph(String.valueOf(priceNetOfProduct),polskieFonty));
                table.addCell(cell13);
                PdfPCell cell14 = new PdfPCell(new Paragraph(String.valueOf(priceNet),polskieFonty));
                table.addCell(cell14);
                PdfPCell cell15 = new PdfPCell(new Paragraph(String.valueOf(duty),polskieFonty));
                table.addCell(cell15);
                PdfPCell cell16 = new PdfPCell(new Paragraph(String.valueOf(dutyPrice),polskieFonty));
                table.addCell(cell16);
                PdfPCell cell17 = new PdfPCell(new Paragraph(String.valueOf(priceGrossOfProduct),polskieFonty));
                table.addCell(cell17);

            }
            cell1 = new PdfPCell(new Paragraph("Razem",new com.itextpdf.text.Font(helvetica,10,Font.BOLD)));
            cell2 = new PdfPCell(new Paragraph("",polskieFonty));
            cell3 = new PdfPCell(new Paragraph("",polskieFonty));
            cell4 = new PdfPCell(new Paragraph(String.valueOf(priceNetOfProducts),new com.itextpdf.text.Font(helvetica,10,Font.BOLD)));
            cell5 = new PdfPCell(new Paragraph("",polskieFonty));
            cell6 = new PdfPCell(new Paragraph(String.valueOf(priceGrossOfProducts),new com.itextpdf.text.Font(helvetica,10,Font.BOLD)));
            cell7 = new PdfPCell(new Paragraph(String.valueOf(priceGross),new com.itextpdf.text.Font(helvetica,10,Font.BOLD)));
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);

            document.add(table);
            document.add(new Paragraph("\n"));

            Paragraph doZaplatyInfo = new Paragraph("Razem do zapłaty: "+priceGross+"zł",polskieFonty);
            doZaplatyInfo.setAlignment(Element.ALIGN_RIGHT);
            document.add(doZaplatyInfo);
            document.add(new Paragraph("\n\n\n"));
            document.add(podpis());
            document.close(); //zamykamy dokument

            //uruchamiamy plik
            File file = new File("F"+idCreateFacture.getText()+".pdf");
            HostServices hostServices = getHostServices();
            hostServices.showDocument(file.getAbsolutePath());

        }else{
            labelInfo.setText("Taka faktura nie istnieje");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }
        idCreateFacture.clear();
        createFactureButton.setDisable(true);
    }

    public void editFacture() throws IOException {
        labelInfo.setText(null);
        ConnectDataBase con = new ConnectDataBase();
        if(con.checkIdFacture(idEditFacture.getText())){
            this.idEditFactureToTransfer=idEditFacture.getText();
            this.editDateToTransfer=con.checkDateFacture(idEditFacture.getText());
            this.idEditCustomerToTransfer=con.checkIdCustomerOfFacture(idEditFacture.getText());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/faktury/fxmls/editFactureMenu.fxml"));
            BorderPane mainmenu = loader.load();
            clearWindow(listFacturesMenu);
            listFacturesMenu.setCenter(mainmenu);
        }else{
            labelInfo.setText("Taka faktura nie istnieje");
            labelInfo.setTextFill(Color.web("#FF0000"));
        }
        idEditFacture.clear();
        editFactureButton.setDisable(true);
    }

    public void stateEditFactureButton(){
        boolean disableButton = idEditFacture.getText().isEmpty() ||
                idEditFacture.getText().trim().isEmpty();
        editFactureButton.setDisable(disableButton);
    }

    public void stateDeleteFactureButton(){
        boolean disableButton = idDeleteFacture.getText().isEmpty() ||
                idDeleteFacture.getText().trim().isEmpty();
        deleteFactureButton.setDisable(disableButton);
    }

    public void stateCreateFactureButton(){
        boolean disableButton = idCreateFacture.getText().isEmpty() ||
                idCreateFacture.getText().trim().isEmpty();
        createFactureButton.setDisable(disableButton);
    }

    public static String getIdEditFactureToTransfer() {
        return idEditFactureToTransfer;
    }

    public static String getDataFactureToTransfer() {
        return editDateToTransfer;
    }

    public static String getIdEditCustomerToTransfer() {
        return idEditCustomerToTransfer;
    }

    private void clearWindow(BorderPane m){
        m.setBottom(null);
        m.setLeft(null);
        m.setRight(null);
        m.setTop(null);
        m.setCenter(null);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
