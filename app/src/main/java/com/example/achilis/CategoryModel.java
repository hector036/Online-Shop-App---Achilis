
package com.example.achilis;
public class CategoryModel {
    private String categoryIconLink;
    private String categoryName;

    public String getCategoryIconLink() {
        return categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        this.categoryIconLink = categoryIconLink;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel(String categoryIconLink, String categoryName) {
        this.categoryIconLink = categoryIconLink;
        this.categoryName = categoryName;
    }
}
