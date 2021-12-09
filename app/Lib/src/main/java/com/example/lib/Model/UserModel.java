package com.example.lib.Model;

public class UserModel {
    private int UserID;
    private String Email;
    private String UserAvatar;
    private String UserFullName;

    public UserModel(int id, String email, String UserAvatar,String  userFullName) {
        this.UserID = id;
        this.Email = email;
        this.UserAvatar = UserAvatar;
        this.UserFullName = userFullName;
    }

    public int getUserID() {
        return UserID;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserAvatar() {
        return UserAvatar;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUserAvatar(String userAvatar) {
        UserAvatar = userAvatar;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }
}
