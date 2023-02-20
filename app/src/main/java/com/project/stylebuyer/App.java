package com.project.stylebuyer;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Category;
import com.project.stylebuyer.firebase.Product;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        Product product = new Product()
//                .setName("Solid Crop Tank Top")
//                .setPrice(9.00)
//                .setCategoryKey("-NOLW9f6aaR-S_vmVqj-")
//                .setImageUrl("");
//
//        Api api = new Api();
//        api.addProduct(product);
    }


}
