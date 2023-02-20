package com.project.stylebuyer.interfaces;

import com.project.stylebuyer.firebase.Category;

public interface CategoryClickedListener {
    void categoryItemClicked(Category category, int position);
}