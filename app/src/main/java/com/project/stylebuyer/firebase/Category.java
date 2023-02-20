package com.project.stylebuyer.firebase;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Category extends Uid implements Serializable {

    private String name;
    private String imageUrl;
    private boolean selected;

    public Category(){
        this.selected = false;
    }

    public Category(String uid, String name, String imageUrl){
        super(uid);
        this.name = name;
        this.imageUrl = imageUrl;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

//    public Uri getImageUri(){
//        return Uri.parse(this.imageUrl);
//    }

    public Category setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Exclude
    public boolean isSelected(){
        return this.selected;
    }

    public void setSelected(Boolean selected){
        this.selected = selected;
    }
}
