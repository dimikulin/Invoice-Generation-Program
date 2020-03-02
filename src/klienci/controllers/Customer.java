package klienci.controllers;

import java.io.*;


public class Customer implements Serializable, Comparable<Customer> {
    private String id;
    private String nameCompany;
    private String nip;
    private String streetCompany;
    private String numberOfStreetCompany;
    private String zipCode;
    private String town;
    private String mobilePhone;

    public Customer(){

    }

    public Customer(String id, String nameCompany, String nip, String streetCopmany, String numberOfStreetCompany, String zipCode, String town, String mobilePhone) {
        this.id= id;
        this.nameCompany = nameCompany;
        this.nip = nip;
        this.streetCompany = streetCopmany;
        this.numberOfStreetCompany = numberOfStreetCompany;
        this.zipCode = zipCode;
        this.town = town;
        this.mobilePhone = mobilePhone;

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }


    public String getNip() {
        return nip;
    }


    public String getStreetCompany() {
        return streetCompany;
    }

    public void setStreetCompany(String streetCompany) {
        this.streetCompany = streetCompany;
    }


    public String getNumberOfStreetCompany() {
        return numberOfStreetCompany;
    }

    public void setNumberOfStreetCompany(String numberOfStreetCompany) {
        this.numberOfStreetCompany = numberOfStreetCompany;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }


    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return id + ". \"" + nameCompany + "\"" +
                " NIP: " + nip +
                ", ul. " + streetCompany + ' ' +
                 numberOfStreetCompany + ' ' +
                 zipCode +' '+town +
                " tel. " + mobilePhone;
    }

    @Override
    public int compareTo(Customer o) {
        if(Integer.parseInt(this.getId())>Integer.parseInt(o.getId())){
            return 1;
        }else{
            return 0;
        }
    }
}
