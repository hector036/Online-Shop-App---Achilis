package com.example.achilis;

import java.util.List;

public class HomePageModel{
    public static final int HORIZONTAL_PRODUCT_VIEW=0;
    public static final int GRID_PRODUCT_VIEW=1;

private int type;


//////////////Horizontal Product Layout

private String title;
private List<HorizontalScrollProductModel> horizontalScrollProductModelList;
private List<WishListModel> viewAllWishList;

    public HomePageModel(int type, String title, List<HorizontalScrollProductModel> horizontalScrollProductModelList,List<WishListModel> viewAllWishList) {
        this.type = type;
        this.title = title;
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
        this.viewAllWishList = viewAllWishList;
    }

    public HomePageModel(int type, String title, List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.type = type;
        this.title = title;
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    public List<WishListModel> getViewAllWishList() {
        return viewAllWishList;
    }

    public void setViewAllWishList(List<WishListModel> viewAllWishList) {
        this.viewAllWishList = viewAllWishList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalScrollProductModel> getHorizontalScrollProductModelList() {
        return horizontalScrollProductModelList;
    }

    public void setHorizontalScrollProductModelList(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    public int getType() {
        return type;
    }

    //////////////Horizontal Product Layout

    /////////////Grid Product Layout

    //No need to code same as Horizontal Layout

    /////////////Grid Product Layout

}
