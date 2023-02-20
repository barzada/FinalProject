package com.project.stylebuyer.interfaces;

import com.project.stylebuyer.firebase.Category;
import com.project.stylebuyer.firebase.Product;
import com.project.stylebuyer.firebase.Order;

import java.util.ArrayList;

public interface FetchDataCallBack {
    void fetchCategoriesComplete(ArrayList<Category> categories);
    void fetchProductsOfCategoryComplete(ArrayList<Product> products);
    void fetchOrdersComplete(ArrayList<Order> orders);
}
