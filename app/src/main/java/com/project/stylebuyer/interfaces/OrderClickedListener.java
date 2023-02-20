package com.project.stylebuyer.interfaces;
import com.project.stylebuyer.firebase.Order;

public interface OrderClickedListener {
    void orderItemClicked(Order order, int position);

}
