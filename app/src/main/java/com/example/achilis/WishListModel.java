package com.example.achilis;

public class WishListModel {
    private String productImage;
    private String prouctTitle;
    private long freeCoupon;
    private String rating;
    private long totalRating;
    private String productPrice;
    private String cuttedPrice;
    private boolean COD;

    public WishListModel(String productImage, String prouctTitle, long freeCoupon, String rating, long totalRating, String productPrice, String cuttedPrice, boolean COD) {
        this.productImage = productImage;
        this.prouctTitle = prouctTitle;
        this.freeCoupon = freeCoupon;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.COD = COD;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProuctTitle() {
        return prouctTitle;
    }

    public void setProuctTitle(String prouctTitle) {
        this.prouctTitle = prouctTitle;
    }

    public long getFreeCoupon() {
        return freeCoupon;
    }

    public void setFreeCoupon(long freeCoupon) {
        this.freeCoupon = freeCoupon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(long totalRating) {
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

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }
}
