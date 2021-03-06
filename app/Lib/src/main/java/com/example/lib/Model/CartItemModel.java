package com.example.lib.Model;

import java.util.ArrayList;
import java.util.List;

public class CartItemModel {

    public  static final int CART_ITEM = 0;
    public  static final int TOTAL_AMOUNT = 1;

    private  int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    private Long freeCoupans, productQuantity, offersApplied, coupansApplied,maxQuantity,stockQuantity;
    private String productID,productImage,productTitle, productPrice,discountedPrice, cuttedPrice,selectedCoupanId;
    private boolean inStock,qtyError,COD;
    private List<String> qtyIDs;

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    //, Long offersApplied


    public CartItemModel(int type, String productID, String productImage, Long freeCoupans, Long productQuantity, Long coupansApplied, String productTitle, String productPrice, String cuttedPrice, boolean inStock  , Long maxQuantity   , Long stockQuantity,boolean COD) {
        this.type=type;
        this.productID=productID;
        this.productImage = productImage;
        this.freeCoupans = freeCoupans;
        this.productQuantity = productQuantity;
        //this.offersApplied = offersApplied;
        this.coupansApplied = coupansApplied;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.inStock=inStock;
        this.maxQuantity=maxQuantity;
        this.stockQuantity=stockQuantity;
        qtyIDs=new ArrayList<>();
        qtyError=false;
        this.COD=COD;

    }

    public CartItemModel(Long productQuantity, String productID, String productImage, String productTitle, String productPrice) {
        this.productQuantity = productQuantity;
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }
    public boolean setCartQty(Long soluong){
        this.productQuantity = soluong;
        return true;
    }
    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }

    public String getSelectedCoupanId() {
        return selectedCoupanId;
    }

    public void setSelectedCoupanId(String selectedCoupanId) {
        this.selectedCoupanId = selectedCoupanId;
    }

    public boolean isQtyError() {
        return qtyError;
    }

    public void setQtyError(boolean qtyError) {
        this.qtyError = qtyError;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getQtyIDs() {
        return qtyIDs;
    }

    public void setQtyIDs(List<String> qtyIDs) {
        this.qtyIDs = qtyIDs;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
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

    public Long getFreeCoupans() {
        return freeCoupans;
    }

    public void setFreeCoupans(Long freeCoupans) {
        this.freeCoupans = freeCoupans;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getCoupansApplied() {
        return coupansApplied;
    }

    public void setCoupansApplied(Long coupansApplied) {
        this.coupansApplied = coupansApplied;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
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

    /////////cart item

    /////////cart total
    private int totalItems,totalItemsPrice,totalAmount,savedAmount;
    private String deliveryPrice;

    public CartItemModel(int type) {
        this.type = type;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(int totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(int savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
/////////cart total

//    ///////////Cart item
//    private int productImage;
//    private String productTitle;
//    private int freeCoupons;
//    private String productPrice;
//    private String cuttedPrice;
//    private int productQuantity;
//    private int offersApplied;
//    private int coupensApplied;
//
//    public CartItemModel(int type, int productImage, String productTitle, int freeCoupons, String productPrice, String cuttedPrice, int productQuantity, int offersApplied, int coupensApplied) {
//        this.type = type;
//        this.productImage = productImage;
//        this.productTitle = productTitle;
//        this.freeCoupons = freeCoupons;
//        this.productPrice = productPrice;
//        this.cuttedPrice = cuttedPrice;
//        this.productQuantity = productQuantity;
//        this.offersApplied = offersApplied;
//        this.coupensApplied = coupensApplied;
//    }
//
//    public int getProductImage() {
//        return productImage;
//    }
//
//    public void setProductImage(int productImage) {
//        this.productImage = productImage;
//    }
//
//    public String getProductTitle() {
//        return productTitle;
//    }
//
//    public void setProductTitle(String productTitle) {
//        this.productTitle = productTitle;
//    }
//
//    public int getFreeCoupons() {
//        return freeCoupons;
//    }
//
//    public void setFreeCoupons(int freeCoupons) {
//        this.freeCoupons = freeCoupons;
//    }
//
//    public String getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(String productPrice) {
//        this.productPrice = productPrice;
//    }
//
//    public String getCuttedPrice() {
//        return cuttedPrice;
//    }
//
//    public void setCuttedPrice(String cuttedPrice) {
//        this.cuttedPrice = cuttedPrice;
//    }
//
//    public int getProductQuantity() {
//        return productQuantity;
//    }
//
//    public void setProductQuantity(int productQuantity) {
//        this.productQuantity = productQuantity;
//    }
//
//    public int getOffersApplied() {
//        return offersApplied;
//    }
//
//    public void setOffersApplied(int offersApplied) {
//        this.offersApplied = offersApplied;
//    }
//
//    public int getCoupensApplied() {
//        return coupensApplied;
//    }
//
//    public void setCoupensApplied(int coupensApplied) {
//        this.coupensApplied = coupensApplied;
//    }
//    ///////////Cart item
//    ////////////////////////////////////////////////////////////////
//    //////////Cart total
//    private String totalItems;
//    private String totalItemPrice;
//    private String deliveryPrice;
//    private String savedAmount;
//    private String totalAmount;
//
//    public CartItemModel(int type, String totalItems, String totalItemPrice, String deliveryPrice, String totalAmount, String savedAmount) {
//        this.totalAmount=totalAmount;
//        this.type = type;
//        this.totalItems = totalItems;
//        this.totalItemPrice = totalItemPrice;
//        this.deliveryPrice = deliveryPrice;
//        this.savedAmount = savedAmount;
//    }
//
//    public String getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(String totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getTotalItems() {
//        return totalItems;
//    }
//
//    public void setTotalItems(String totalItems) {
//        this.totalItems = totalItems;
//    }
//
//    public String getTotalItemPrice() {
//        return totalItemPrice;
//    }
//
//    public void setTotalItemPrice(String totalItemPrice) {
//        this.totalItemPrice = totalItemPrice;
//    }
//
//    public String getDeliveryPrice() {
//        return deliveryPrice;
//    }
//
//    public void setDeliveryPrice(String deliveryPrice) {
//        this.deliveryPrice = deliveryPrice;
//    }
//
//    public String getSavedAmount() {
//        return savedAmount;
//    }
//
//    public void setSavedAmount(String savedAmount) {
//        this.savedAmount = savedAmount;
//    }
////////////Cart total
}
