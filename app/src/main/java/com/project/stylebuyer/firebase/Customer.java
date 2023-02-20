package com.project.stylebuyer.firebase;

import android.net.Uri;
import com.google.firebase.database.Exclude;
import java.io.Serializable;

public class Customer extends Uid implements Serializable {

    private String fullName;
    private String email;
    private String password;
    private Uri imgUrl;

    public Customer(String fullName, String email, String password){
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public Customer(){}

    public String getFullName() {
        return fullName;
    }

    public Customer setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public Customer setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail(){
        return this.email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public Uri getImgUrl() {
        return imgUrl;
    }

    public Customer setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }
}
