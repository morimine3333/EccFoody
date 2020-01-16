package com.example.a2170188.navigationdrawractivity;

import java.util.List;

//Database2の内部クラス等で使いたい変数を保持するクラス
class Database2Value {
    private int num;
    private String userID;
    private String storeID;
    private String commentID;
    private String date;
    private String text;
    private List<String> photolist;
    private String firstname;
    private String lastname;
    private String icon;
    private String storeName;

    void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    void setUserID(String userID) {
        this.userID = userID;
    }


    void setNum(int num) {
        this.num = num;
    }

     void setStoreID(String storeID) {
        this.storeID = storeID;
    }

     void setDate(String date) {
        this.date = date;
    }

     void setText(String text) {
        this.text = text;
    }

     void setPhotolist(List<String> photolist) {
        this.photolist = photolist;
    }

     void setFirstname(String firstname) {
        this.firstname = firstname;
    }

     void setLastname(String lastname) {
        this.lastname = lastname;
    }

     void setIcon(String icon) {
        this.icon = icon;
    }

     void setStoreName(String storeName) {
        this.storeName = storeName;
    }

     int getNum() {
        return num;
    }

     String getStoreID() {
        return storeID;
    }

     String getDate() {
        return date;
    }

     String getText() {
        return text;
    }

     List<String> getPhotolist() {
        return photolist;
    }

     String getFirstname() {
        return firstname;
    }

     String getLastname() {
        return lastname;
    }

    String getIcon() {
        return icon;
    }

     String getStoreName() {
        return storeName;
    }

     String getCommentID() {
        return commentID;
    }

     String getUserID() {
        return userID;
    }
}
