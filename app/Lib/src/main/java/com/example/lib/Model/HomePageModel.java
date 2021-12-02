package com.example.lib.Model;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int HORIZONTAL_PRODUCT_VIEW = 1;
    public static final int GRID_PRODUCT_VIEW = 2;

    private int type;
    private String backgroundColor;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

/////Banner Slider
    private List<SliderModel> sliderModelList;
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
/////Banner Slider

///////horizontal product view and grid product view
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public HomePageModel(int type, String title,String backgroundColor,List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.backgroundColor = backgroundColor;
        this.title = title;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    ///horizontal product view only
    private List<WishlistModel> viewAllProductList;

    public HomePageModel(int type, String title, String backgroundColor,List<HorizontalProductScrollModel> horizontalProductScrollModelList,List<WishlistModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductList=viewAllProductList;
    }

    public List<WishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    ///horizontal product view only

///////horizontal product view and grid product view
}
