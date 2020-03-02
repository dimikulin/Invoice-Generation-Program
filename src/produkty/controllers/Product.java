package produkty.controllers;

public class Product implements Comparable<Product>{
    private String id;
    private String nameProduct;
    private String priceNet;
    private String vat;

    public Product(String id, String nameProduct, String priceNet, String vat) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.priceNet = priceNet;
        this.vat = vat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(String priceNet) {
        this.priceNet = priceNet;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    @Override
    public String toString() {
        return id + ". " + nameProduct
                +", Cena netto = " + priceNet +
                " zł, VAT = " + vat+"%";
    }

    @Override
    public int compareTo(Product o) {
        if(Integer.parseInt(this.getId())>Integer.parseInt(o.getId())){
            return 1;
        }else{
            return 0;
        }
    }
}
