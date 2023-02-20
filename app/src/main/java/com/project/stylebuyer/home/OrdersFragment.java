package com.project.stylebuyer.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.project.stylebuyer.R;
import com.project.stylebuyer.adapters.OrderAdapter;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Category;
import com.project.stylebuyer.firebase.Order;
import com.project.stylebuyer.firebase.Product;
import com.project.stylebuyer.interfaces.FetchDataCallBack;
import com.project.stylebuyer.interfaces.OrderClickedListener;

import java.util.ArrayList;
import java.util.Objects;


public class OrdersFragment extends Fragment {
    private Activity activity;
    private Api api;
    private ArrayList<Order> orders;
    private RecyclerView orders_RV_orders;
    private OrderAdapter orderAdapter;
    private ProgressBar orders_PB_loading;
    private Order selectedOrder;
    private LinearLayout orders_LL_emptyOrders;


    public OrdersFragment(Activity activity){
        this.activity = activity;
        orders = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_orders, container, false);
        findViews(root);
        initVars();
        return root;
    }

    private void initVars() {
        orders_PB_loading.setVisibility(View.VISIBLE);
        api = new Api();
        api.setFetchDataCallBack(new FetchDataCallBack() {
            @Override
            public void fetchCategoriesComplete(ArrayList<Category> categories) {

            }

            @Override
            public void fetchProductsOfCategoryComplete(ArrayList<Product> products) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void fetchOrdersComplete(ArrayList<Order> ordersLst) {
                if (ordersLst.size() == 0){
                    orders_LL_emptyOrders.setVisibility(View.VISIBLE);
                }else{
                    orders_LL_emptyOrders.setVisibility(View.INVISIBLE);
                }
                orders = ordersLst;
                orderAdapter.setOrders(orders);
                Objects.requireNonNull(orders_RV_orders.getAdapter()).notifyDataSetChanged();
                orders_PB_loading.setVisibility(View.INVISIBLE);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        api.fetchClientOrders(api.getCurrentUser().getUid());
        orderAdapter = new OrderAdapter(activity, orders);
        orderAdapter.setOrderClickedListener(new OrderClickedListener() {
            @Override
            public void orderItemClicked(Order order, int position) {
                selectedOrder = order;
                builder.setMessage("Are you sure you want to remove the order?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        orders_RV_orders.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        orders_RV_orders.setHasFixedSize(true);
        orders_RV_orders.setItemAnimator(new DefaultItemAnimator());
        orders_RV_orders.setAdapter(orderAdapter);

    }

    private void findViews(View root) {
        orders_RV_orders = root.findViewById(R.id.orders_RV_orders);
        orders_PB_loading = root.findViewById(R.id.orders_PB_loading);
        orders_LL_emptyOrders = root.findViewById(R.id.orders_LL_emptyOrders);
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    if(selectedOrder != null){
                        api.removeOrder(selectedOrder);
                    }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    selectedOrder = null;
                    break;
            }
        }
    };
}