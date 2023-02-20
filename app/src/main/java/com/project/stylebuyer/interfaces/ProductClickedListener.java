package com.project.stylebuyer.interfaces;
import com.project.stylebuyer.firebase.Product;

public interface ProductClickedListener {
    void productItemClicked(Product product, int position);

}
