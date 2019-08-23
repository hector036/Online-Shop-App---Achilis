package com.example.achilis;

public class CartItemModel {

    public static final int CART_ITEM =0;
    public static final int TOTAL_AMOUNT =1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    ////// cart item/////
    private String productID;
    private String productImage;
    private String productTitle;
    private long freeCoupons;
    private String productPrice;
    private String cuttedPrice;
    private long productQty;
    private long offerApplied;
    private long couponApplied;

    public CartItemModel(int type,String productID, String productImage, String productTitle, long freeCoupons, String productPrice, String cuttedPrice, long productQty, long offerApplied, long couponApplied) {
        this.type = type;
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupons = freeCoupons;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQty = productQty;
        this.offerApplied = offerApplied;
        this.couponApplied = couponApplied;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(int freeCoupons) {
        this.freeCoupons = freeCoupons;
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

    public long getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public long getOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(int offerApplied) {
        this.offerApplied = offerApplied;
    }

    public long getCouponApplied() {
        return couponApplied;
    }

    public void setCouponApplied(int couponApplied) {
        this.couponApplied = couponApplied;
    }
    ////// cart item/////

    /////// cart total/////

    public CartItemModel(int type) {
        this.type = type;
    }

    /////// cart total/////
}
