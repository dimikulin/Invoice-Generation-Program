package BazaDanych;

import faktury.controllers.Facture;
import faktury.controllers.ListFacturesMenu;
import faktury.controllers.RowProduct;
import klienci.controllers.Customer;
import produkty.controllers.Product;

import java.nio.file.Paths;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConnectDataBase {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:src/BazaDanych/bazadanych.db";
    private Connection conn;
    private Statement stat;

    public ConnectDataBase() {
        try {
            Class.forName(ConnectDataBase.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
    }

    public boolean editCustomer(Customer c,String id){
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE klienci SET id=?, nameCompany=?, nip=?, street=?, " +
                    "numberOfStreet=?, zipCode=?, town=?, mobilePhone=? WHERE id=?");
            prepStmt.setString(1,c.getId());
            prepStmt.setString(2,c.getNameCompany());
            prepStmt.setString(3,c.getNip());
            prepStmt.setString(4,c.getStreetCompany());
            prepStmt.setString(5,c.getNumberOfStreetCompany());
            prepStmt.setString(6,c.getZipCode());
            prepStmt.setString(7,c.getTown());
            prepStmt.setString(8,c.getMobilePhone());
            prepStmt.setString(9,id);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<String> showProductsOfFacture(String id){
        List<String> listProducts = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT p.nameProduct FROM produktyFaktur f INNER JOIN produkty p ON f.idProduktu=p.id WHERE idFaktury="+id);
            while(result.next()) {
                listProducts.add(result.getString("nameProduct"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return listProducts;
    }


    public boolean editProduct(Product p,String id){
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE produkty SET id=?, nameProduct=?, priceNet=?, vat=? WHERE id=?");
            prepStmt.setString(1,p.getId());
            prepStmt.setString(2,p.getNameProduct());
            prepStmt.setString(3,p.getPriceNet());
            prepStmt.setString(4,p.getVat());
            prepStmt.setString(5,id);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String checkIdCustomerOfFacture(String id){
        String idCustomer = null;
        try {
            ResultSet result = stat.executeQuery("SELECT idCompany FROM 'faktury' WHERE id="+id);
            while(result.next()) {
                idCustomer =result.getString("idCompany");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return idCustomer;
    }

    public boolean checkIdCustomer(String id){
        boolean idCustomer=false;
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM 'klienci' WHERE id="+id);
            while(result.next()) {
             idCustomer=true;
            }
        } catch (SQLException e) {
            return false;
        }
        return idCustomer;
    }

    public boolean checkIdProduct(String id){
        boolean idProduct=false;
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM 'produkty' WHERE id="+id);
            while(result.next()) {
                idProduct=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return idProduct;
    }

    public List<String> getFacturesWithThisProduct(String idProduct){
        List<String> faktures = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT DISTINCT idFaktury FROM 'produktyFaktur' WHERE idProduktu="+idProduct);
            String id;
            while(result.next()) {
                id = result.getString("idFaktury");
                faktures.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return faktures;
    }

    public boolean checkIdFacture(String id){
        boolean idFacture=false;
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM 'faktury' WHERE id="+id);
            while(result.next()) {
                idFacture=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return idFacture;
    }

    public String getVATofProduct(String id){
        String vat=null;
        try {
            ResultSet result = stat.executeQuery("SELECT vat FROM 'produkty' WHERE id="+id);
            while(result.next()) {
                vat=result.getString("vat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vat;
    }

    public String getPriceNetOfProduct(String id){
        String priceNet=null;
        try {
            ResultSet result = stat.executeQuery("SELECT priceNet FROM 'produkty' WHERE id="+id);
            while(result.next()) {
                priceNet=result.getString("priceNet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return priceNet;
    }

    public String checkDateFacture(String id){
        String date = null;
        try {
            ResultSet result = stat.executeQuery("SELECT data FROM 'faktury' WHERE id="+id);
            while(result.next()) {
                date=result.getString("data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public List<RowProduct> selectProductsOfFactures(String id){
        List<RowProduct> produkty = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT idProduktu, amount FROM 'produktyFaktur' WHERE idFaktury="+id);
            String idProduktu;
            String amount;

            while(result.next()) {
                idProduktu = result.getString("idProduktu");
                amount = result.getString("amount");
                produkty.add(new RowProduct(idProduktu,amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return produkty;
    }

    public boolean editMyCompany(Customer c){
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE mojaFirma SET nameCompany=?, nip=?, street=?, numberOfStreet=?, zipCode=?, town=?, mobilePhone=? WHERE id=1");
            prepStmt.setString(1,c.getNameCompany());
            prepStmt.setString(2,c.getNip());
            prepStmt.setString(3,c.getStreetCompany());
            prepStmt.setString(4,c.getNumberOfStreetCompany());
            prepStmt.setString(5,c.getZipCode());
            prepStmt.setString(6,c.getTown());
            prepStmt.setString(7,c.getMobilePhone());
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Customer myCompany(){
        Customer myCompany = new Customer();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM mojaFirma WHERE id=1");
            String id;
            String nameCompany;
            String nip;
            String street;
            String numberOfStreet;
            String zipCode;
            String town;
            String mobilePhone;
            while(result.next()) {
                id = result.getString("id");
                nameCompany = result.getString("nameCompany");
                nip = result.getString("nip");
                street = result.getString("street");
                numberOfStreet = result.getString("numberOfStreet");
                zipCode = result.getString("zipCode");
                town = result.getString("town");
                mobilePhone = result.getString("mobilePhone");
                myCompany = new Customer(id,nameCompany,nip,street,numberOfStreet,zipCode,town,mobilePhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return myCompany;
    }

    public Customer getCustomer(String idd){
        Customer myCompany = new Customer();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM klienci WHERE id="+idd);
            String id;
            String nameCompany;
            String nip;
            String street;
            String numberOfStreet;
            String zipCode;
            String town;
            String mobilePhone;
            while(result.next()) {
                id = result.getString("id");
                nameCompany = result.getString("nameCompany");
                nip = result.getString("nip");
                street = result.getString("street");
                numberOfStreet = result.getString("numberOfStreet");
                zipCode = result.getString("zipCode");
                town = result.getString("town");
                mobilePhone = result.getString("mobilePhone");
                myCompany = new Customer(id,nameCompany,nip,street,numberOfStreet,zipCode,town,mobilePhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return myCompany;
    }

    public String getDateFacture(String id){
        String date=null;
        try {
            ResultSet result = stat.executeQuery("SELECT data FROM faktury WHERE id="+id);
            while(result.next()) {
                date = result.getString("data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            id=null;
        }
        if(id==null){
            return "0";
        }
        return date;
    }

    public List<Facture> selectFactures(){
        List<Facture> factures = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateformater;
        try {
            ResultSet result = stat.executeQuery("SELECT f.id, f.idCompany, e.nameCompany, f.data, f.priceNet, f.priceGross FROM faktury f INNER JOIN klienci e ON e.id=f.idCompany ");
            String id;
            String idCompany;
            String date;
            String priceNet;
            String priceGross;
            String nameCompany;
            while(result.next()) {
                id = result.getString("id");
                idCompany = result.getString("idCompany");
                date = result.getString("data");
                priceNet = result.getString("priceNet");
                priceGross = result.getString("priceGross");
                dateformater = LocalDate.parse(date,formatter);
                nameCompany = result.getString("nameCompany");
                factures.add(new Facture(id,idCompany,nameCompany,dateformater,priceNet,priceGross));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return factures;
    }

    public List<Customer> selectCustomers() {
        List<Customer> klienci = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM 'klienci'");
            String id;
            String nameCompany;
            String nip;
            String street;
            String numberOfStreet;
            String zipCode;
            String town;
            String mobilePhone;
            while(result.next()) {
                id = result.getString("id");
                nameCompany = result.getString("nameCompany");
                nip = result.getString("nip");
                street = result.getString("street");
                numberOfStreet = result.getString("numberOfStreet");
                zipCode = result.getString("zipCode");
                town = result.getString("town");
                mobilePhone = result.getString("mobilePhone");
                klienci.add(new Customer(id, nameCompany, nip,street,numberOfStreet,zipCode,town,mobilePhone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return klienci;
    }

    public List<Product> selectProducts() {
        List<Product> produkty = new ArrayList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM 'produkty'");
            String id;
            String nameProduct;
            String priceNet;
            String vat;
            while(result.next()) {
                id = result.getString("id");
                nameProduct = result.getString("nameProduct");
                priceNet = result.getString("priceNet");
                vat = result.getString("vat");
                produkty.add(new Product(id, nameProduct, priceNet,vat));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return produkty;
    }

    public String getLastIdProduct(){
        String id=null;
        try {
            ResultSet result = stat.executeQuery("SELECT MAX(id) as id FROM produkty");
            while(result.next()) {
                id = result.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            id=null;
        }
        if(id==null){
            return "0";
        }
        return id;
    }

    public boolean addFacture(Facture f) throws SQLException {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into faktury values (?, ?, ?, ?, ?);");
            prepStmt.setString(1,String.valueOf(Integer.parseInt(this.getLastIdFacture())+1));
            prepStmt.setString(2, f.getIdCompany());
            prepStmt.setString(3, String.valueOf(f.getData()));
            prepStmt.setString(4, null);
            prepStmt.setString(5, null);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu faktury do bazy"+e);
            return false;
        }
        return true;
    }

    public boolean deleteRowsFacture(String id) throws SQLException {
            try {
                PreparedStatement prepStmtDelete = conn.prepareStatement("DELETE FROM produktyFaktur WHERE idFaktury="+id);
                prepStmtDelete.executeUpdate();
                prepStmtDelete.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }

    public boolean deleteFacture(String id) throws SQLException {
        if(deleteRowsFacture(id)){
            System.out.println("Usunąłem produkty faktury");
            try {
                PreparedStatement prepStmtDelete = conn.prepareStatement("DELETE FROM faktury WHERE id=" + id);
                prepStmtDelete.executeUpdate();
                prepStmtDelete.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            System.out.println("Nie usunałem produktow faktury");
            return false;
        }
    }

    public boolean updatePriceFacture(String idFacture,double priceNet, double priceGross){
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE faktury SET priceNet=?, priceGross=? WHERE id=?");
            prepStmt.setString(1,String.valueOf(priceNet));
            prepStmt.setString(2,String.valueOf(priceGross));
            prepStmt.setString(3,idFacture);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addRowFacture(String idFacture, String idProduct, String amount){
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into produktyFaktur values (null, ?, ?, ?);");
            prepStmt.setString(1,idFacture);
            prepStmt.setString(2, idProduct);
            prepStmt.setString(3, amount);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu wiersza faktury do bazy"+e);
            return false;
        }
        return true;
    }

    public String getLastIdFacture(){
        String id=null;
        try {
            ResultSet result = stat.executeQuery("SELECT MAX(id) as id FROM faktury");
            while(result.next()) {
                id = result.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            id=null;
        }
        if(id==null){
            return "0";
        }
        return id;
    }

    public String getLastIdCustomer(){
        String id=null;
        try {
            ResultSet result = stat.executeQuery("SELECT MAX(id) as id FROM klienci");
            while(result.next()) {
                id = result.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            id=null;
        }
        if(id==null){
            return "0";
        }
        return id;
    }

    public boolean addCustomer(Customer a) throws SQLException {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into klienci values (?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, String.valueOf(Integer.parseInt(this.getLastIdCustomer())+1));
            prepStmt.setString(2, a.getNameCompany());
            prepStmt.setString(3, a.getNip());
            prepStmt.setString(4, a.getStreetCompany());
            prepStmt.setString(5, a.getNumberOfStreetCompany());
            prepStmt.setString(6, a.getZipCode());
            prepStmt.setString(7, a.getTown());
            prepStmt.setString(8, a.getMobilePhone());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu klienta do bazy: "+e);
            return false;
        }
        return true;
    }

    public boolean nipExist(String nip){
        boolean exist = false;
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM klienci where nip='"+nip+"'");
            while(result.next()) {
                exist=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
        }
        return exist;
    }

    public boolean addProduct(Product p) throws SQLException {

        try {

            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into produkty values (?, ?, ?, ?);");
            prepStmt.setString(1,String.valueOf(Integer.parseInt(this.getLastIdProduct())+1));
            prepStmt.setString(2, p.getNameProduct());
            prepStmt.setString(3, String.valueOf(Double.parseDouble(p.getPriceNet())));
            prepStmt.setString(4, String.valueOf(Double.parseDouble(p.getVat())));

            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Błąd przy dodawaniu productu do bazy"+e);
            return false;
        }
        return true;
    }

}
