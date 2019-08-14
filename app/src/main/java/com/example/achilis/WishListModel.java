package com.example.achilis;

public class WishListModel {
    private int productImage;
    private String prouctTitle;
    private int freeCoupon;
    private String rating;
    private int totalRating;
    private String productPrice;
    private String cuttedPrice;
    private String paymentMethods;

    public WishListModel(int productImage, String prouctTitle, int freeCoupon, String rating, int totalRating, String productPrice, String cuttedPrice, String paymentMethods) {
        this.productImage = productImage;
        this.prouctTitle = prouctTitle;
        this.freeCoupon = freeCoupon;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.paymentMethods = paymentMethods;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProuctTitle() {
        return prouctTitle;
    }

    public void setProuctTitle(String prouctTitle) {
        this.prouctTitle = prouctTitle;
    }

    public int getFreeCoupon() {
        return freeCoupon;
    }

    public void setFreeCoupon(int freeCoupon) {
        this.freeCoupon = freeCoupon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
