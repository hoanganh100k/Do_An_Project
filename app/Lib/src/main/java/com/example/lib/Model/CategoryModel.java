package com.example.lib.Model;

public class CategoryModel {
    //Tạo trước 2 thuộc tính để design, các thuộc tính còn lại dựa vào thiết kế
    private int CategoryID;
    private String CategoryLink;
    private String CategoryName;

    public CategoryModel(int categoryID,String categoryLink, String categoryName) {
        CategoryLink = categoryLink;
        CategoryName = categoryName;
        CategoryID = categoryID;
    }

    public String getCategoryLink() {
        return CategoryLink;
    }

    public void setCategoryLink(String categoryLink) {
        CategoryLink = categoryLink;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void getCategoryID(int categoryID) {
        CategoryID = categoryID;
    }
}
