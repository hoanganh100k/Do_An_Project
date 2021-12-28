package com.example.e_commerce;

public class CartModel {
    private String title;
    private String price;
    private int quantily;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantily() {
        return quantily;
    }

    public void setQuantily(int quantily) {
        this.quantily = quantily;
    }

    public CartModel(String title, String price, int quantily) {
        this.title = title;
        this.price = price;
        this.quantily = quantily;
    }

}
