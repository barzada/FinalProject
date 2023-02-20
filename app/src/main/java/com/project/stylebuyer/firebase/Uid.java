package com.project.stylebuyer.firebase;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Uid implements Serializable {

    protected String uid;
    public Uid(){}
    public Uid(String uid){
        this.uid = uid;
    }
    @Exclude
    public String getUid(){
        return uid;
    }

    public void setUid(String uid){
        this.uid = uid;
    }
}