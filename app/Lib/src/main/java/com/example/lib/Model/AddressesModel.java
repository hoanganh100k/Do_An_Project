package com.example.lib.Model;

public class AddressesModel {


    private String city, locality, flatNo, pincode, landmark, name, mobileNo, alternateMobileNo, state;
    private Boolean selected;

    public AddressesModel(String city, String locality, String flatNo, String landmark, String name, String mobileNo, Boolean selected) {
        this.city = city;
        this.locality = locality;
        this.flatNo = flatNo;
        this.landmark = landmark;
        this.name = name;
        this.mobileNo = mobileNo;
        this.selected = selected;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateMobileNo() {
        return alternateMobileNo;
    }

    public void setAlternateMobileNo(String alternateMobileNo) {
        this.alternateMobileNo = alternateMobileNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

//    private String fullName;
//    private String address;
//    private Boolean selected;
//
//    public AddressesModel() {
//    }
//
//    public AddressesModel(String fullName, String address,Boolean selected) {
//        this.fullName = fullName;
//        this.address = address;
//        this.selected = selected;
//    }
//
//    public Boolean getSelected() {
//        return selected;
//    }
//
//    public void setSelected(Boolean selected) {
//        this.selected = selected;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
}
