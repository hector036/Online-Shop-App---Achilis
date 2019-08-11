package com.example.achilis;

public class HorizontalScrollProductModel {

    private int productImage;
    private String productTitle;
    private String productDes;
    private String productPrice;

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productIamge) {
        this.productImage = productIamge;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public HorizontalScrollProductModel(int productImage, String productTitle, String productDes, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productDes = productDes;
        this.productPrice = productPrice;
    }
}
