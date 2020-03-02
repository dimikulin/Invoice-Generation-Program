package faktury.controllers;

import produkty.controllers.Product;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Facture {
    private String id;
    private String idCompany;
    private LocalDate data;
    private String priceNet;
    private String priceGross;
    private String nameCompany; //do tabeli w liscie faktur

    public Facture(String idCompany, LocalDate data) {
        this.idCompany = idCompany;
        this.data = data;
    }

    public Facture(String id,String idCompany,String nameCompany, LocalDate data, String priceNet, String priceGross) {
        this.id=id;
        this.idCompany=idCompany;
        this.nameCompany = nameCompany;
        this.data = data;
        this.priceNet=priceNet;
        this.priceGross=priceGross;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(String priceNet) {
        this.priceNet = priceNet;
    }

    public String getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(String priceGross) {
        this.priceGross = priceGross;
    }

    //    public double getPriceNet(){
//        AtomicReference<Double> cena= new AtomicReference<>((double) 0);
//        this.listaProduktow.forEach((k, v) ->cena.set(cena.get()+Double.parseDouble(k.getPriceNet()) * v));
//        double wartosc;
//        wartosc = cena.get()*100; // pi = pi * 100;
//        wartosc = Math.round(wartosc);
//        wartosc /= 100;
//        return wartosc;
//    }
//
//    public double getVat(){
//        AtomicReference<Double> cena= new AtomicReference<>((double) 0);
//        this.listaProduktow.forEach((k, v) ->{
//            double podatek;
//            podatek = v* Double.parseDouble(k.getPriceNet()) * Double.parseDouble(k.getVat());
//            podatek = Math.round(podatek);
//            podatek /= 100;
//            cena.set(cena.get()+(podatek));
//            double wartosc;
//            wartosc = cena.get()*100;
//            wartosc = Math.round(wartosc);
//            wartosc /= 100;
//            cena.set(wartosc);
//        });
//        double wartosc;
//        wartosc = cena.get()*100; // pi = pi * 100;
//        wartosc = Math.round(wartosc);
//        wartosc /= 100;
//        return wartosc;
//    }
//
//    public double obliczWartoscBrutto(){
//        double iloczyn;
//        iloczyn = (getPriceNet()+getVat())*100; // pi = pi * 100;
//        iloczyn = Math.round(iloczyn);
//        iloczyn /= 100;
//        return iloczyn;
//    }

}
