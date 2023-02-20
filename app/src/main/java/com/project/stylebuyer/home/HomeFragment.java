package com.project.stylebuyer.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.project.stylebuyer.R;
import com.project.stylebuyer.adapters.CategoryAdapter;
import com.project.stylebuyer.adapters.ProductAdapter;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Category;
import com.project.stylebuyer.firebase.Order;
import com.project.stylebuyer.firebase.Product;
import com.project.stylebuyer.interfaces.CategoryClickedListener;
import com.project.stylebuyer.interfaces.FetchDataCallBack;
import com.project.stylebuyer.interfaces.ProductClickedListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private Activity activity;
    private Api api;
    private ArrayList<Category> categories;
    private ArrayList<Product> products;
    private RecyclerView home_RV_categories, home_RV_products;
    private ProgressBar home_PB_loading;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;

    public HomeFragment(Activity activity){
        this.activity = activity;
        this.api = new Api();
        categories = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(root);
        initVars();
        return root;
    }

    private void findViews(View root) {
        home_RV_categories = root.findViewById(R.id.home_RV_categories);
        home_RV_products = root.findViewById(R.id.home_RV_products);
        home_PB_loading = root.findViewById(R.id.home_PB_loading);
    }

    private void initVars() {
        home_PB_loading.setVisibility(View.VISIBLE);
        api.setFetchDataCallBack(new FetchDataCallBack() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void fetchCategoriesComplete(ArrayList<Category> categoriesLst) {
                categoriesLst.get(0).setSelected(true);
                categories = categoriesLst;
                categoryAdapter.setCategories(categories);
                Objects.requireNonNull(home_RV_categories.getAdapter()).notifyDataSetChanged();
                api.fetchProductsOfCategory(categoriesLst.get(0));
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void fetchProductsOfCategoryComplete(ArrayList<Product> productsLst) {
                products = productsLst;
                productAdapter.setProducts(products);
                Objects.requireNonNull(home_RV_products.getAdapter()).notifyDataSetChanged();
                home_PB_loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void fetchOrdersComplete(ArrayList<Order> orders) {

            }
        });

        api.fetchCategories();
        categoryAdapter =  new CategoryAdapter(activity, categories);
        categoryAdapter.setCategoryClickedListener(new CategoryClickedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void categoryItemClicked(Category category, int position) {
                Objects.requireNonNull(home_RV_categories.getAdapter()).notifyDataSetChanged();
                api.fetchProductsOfCategory(category);
            }
        });
        home_RV_categories.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        home_RV_categories.setHasFixedSize(true);
        home_RV_categories.setItemAnimator(new DefaultItemAnimator());
        home_RV_categories.setAdapter(categoryAdapter);

        productAdapter = new ProductAdapter(activity, products);
        productAdapter.setProductClickedListener(new ProductClickedListener() {
            @Override
            public void productItemClicked(Product product, int position) {
                Intent intent = new Intent(activity, ProductActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);

            }
        });

        home_RV_products.setLayoutManager(new GridLayoutManager(activity, 2));
        home_RV_products.setHasFixedSize(true);
        home_RV_products.setItemAnimator(new DefaultItemAnimator());
        home_RV_products.setAdapter(productAdapter);

    }

}