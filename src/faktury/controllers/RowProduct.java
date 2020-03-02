package faktury.controllers;

public class RowProduct {
    private String idProduct;
    private String amount;

    public RowProduct(String idProduct, String amount) {
        this.idProduct = idProduct;
        this.amount= amount;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }



}
