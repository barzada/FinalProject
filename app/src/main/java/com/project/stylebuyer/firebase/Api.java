package com.project.stylebuyer.firebase;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.project.stylebuyer.interfaces.FetchDataCallBack;
import com.project.stylebuyer.interfaces.LoginCallBack;

import java.util.ArrayList;
import java.util.Collections;

public class Api {
    public static final String CATEGORY = "Category";
    public static final String PRODUCT = "Product";
    public static final String ORDER = "Order";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;

    private LoginCallBack loginCallBack;
    private FetchDataCallBack fetchDataCallBack;

    public Api(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
    }

    public void setLoginCallBack(LoginCallBack loginCallBack){
        this.loginCallBack = loginCallBack;
    }

    public void setFetchDataCallBack(FetchDataCallBack fetchDataCallBack){
        this.fetchDataCallBack = fetchDataCallBack;
    }

    public void login(Customer customer, boolean create) {
        if(create){
            this.mAuth.createUserWithEmailAndPassword(customer.getEmail(), customer.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(loginCallBack != null){
                                if(task.isSuccessful()){
                                    updateUserFullName(customer.getFullName());
                                    loginCallBack.loginSuccess();
                                }else{
                                    loginCallBack.loginFailed(task.getException().getMessage());
                                }
                            }
                        }
                    });
        }else{
            this.mAuth.signInWithEmailAndPassword(customer.getEmail(), customer.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(loginCallBack != null){
                                if(task.isSuccessful()){
                                    loginCallBack.loginSuccess();
                                }else{
                                    loginCallBack.loginFailed(task.getException().getMessage());
                                }
                            }
                        }
                    });
        }

    }

    public FirebaseUser getCurrentUser(){
        return this.mAuth.getCurrentUser();
    }

    public void updateUserFullName(String name){
        FirebaseUser user = getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(profileUpdates);
    }

    public void updateUserProfileImage(Uri image){
        FirebaseUser user = getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(image)
                .build();
        user.updateProfile(profileUpdates);
    }

    public void changePassword(String password){
        this.getCurrentUser().updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful())
                        Log.d("DEBUG", task.getException().getMessage());
                    }
                });
    }
    public void logout() {
        mAuth.signOut();
    }

    public void addCategory(Category category){
        this.mDatabase.getReference(CATEGORY).push().setValue(category)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("DEBUG1", task.isSuccessful() + "");
                    }
                });

    }

    public void addProduct(Product product) {
        this.mDatabase.getReference(PRODUCT).push().setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("DEBUG1", task.isSuccessful() + "");
                    }
                });
    }

    public void fetchCategories(){
        this.mDatabase.getReference(CATEGORY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Category> categories = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Category category = snap.getValue(Category.class);
                    assert category != null;
                    category.setUid(snap.getKey());
                    categories.add(category);
                }

                if(fetchDataCallBack != null){
                    fetchDataCallBack.fetchCategoriesComplete(categories);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchProductsOfCategory(Category category){
        this.mDatabase.getReference(PRODUCT).orderByChild("categoryKey").equalTo(category.getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Product> products = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Product product = snap.getValue(Product.class);
                    assert product != null;
                    product.setUid(snap.getKey());
                    products.add(product);
                }
                if(fetchDataCallBack != null){
                    fetchDataCallBack.fetchProductsOfCategoryComplete(products);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addOrder(Order order) {
        this.mDatabase.getReference(ORDER).push().setValue(order);
    }

    public void fetchClientOrders(String uid){
        this.mDatabase.getReference(ORDER).orderByChild("clientUid").equalTo(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Order> orders = new ArrayList<>();
                        for (DataSnapshot snap : snapshot.getChildren()){
                            Order order = snap.getValue(Order.class);
                            assert order != null;
                            order.setUid(snap.getKey());
                            orders.add(order);
                        }

                        Collections.reverse(orders);
                        if(fetchDataCallBack != null){
                            fetchDataCallBack.fetchOrdersComplete(orders);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void removeOrder(Order order){
        this.mDatabase.getReference(ORDER).child(order.getUid()).removeValue();
    }
}
