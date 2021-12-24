package com.example.lib.Model;

public class CategoryModel {
    //Tạo trước 2 thuộc tính để design, các thuộc tính còn lại dựa vào thiết kế
    private String CategoryID;
    private String CategoryLink;
    private String CategoryName;
    private  int categoryType;
    public CategoryModel(String categoryID,String categoryLink, String categoryName,int categoryTy) {
        CategoryLink = categoryLink;
        CategoryName = categoryName;
        CategoryID = categoryID;
        categoryType = categoryTy;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
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

    public String getCategoryID() {
        return CategoryID;
    }

    public void getCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
}
