package com.project.stylebuyer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.stylebuyer.R;
import com.project.stylebuyer.firebase.Category;
import com.project.stylebuyer.interfaces.CategoryClickedListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<Category> categories;
    private CategoryClickedListener categoryClickedListener;

    public CategoryAdapter(Activity activity, ArrayList<Category> categories){
        this.activity = activity;
        this.categories = categories;
    }

    public void setCategories(ArrayList<Category> categories){
        this.categories = categories;
    }

    public CategoryAdapter setCategoryClickedListener(CategoryClickedListener categoryClickedListener) {
        this.categoryClickedListener = categoryClickedListener;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
        Category category = getItem(position);
        if(category.isSelected()){
            categoryViewHolder.category_RL_container.setBackgroundColor(activity.getResources().getColor(R.color.primary));
        }else{
            categoryViewHolder.category_RL_container.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }

        categoryViewHolder.category_TV_title.setText(category.getName());
        Glide.with(activity).load(category.getImageUrl()).into(categoryViewHolder.category_IMG_icon);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private Category getItem(int position) {
        return categories.get(position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout category_RL_container;
        public ImageView category_IMG_icon;
        public TextView category_TV_title;


        public CategoryViewHolder(final View itemView) {
            super(itemView);
            this.category_RL_container = itemView.findViewById(R.id.category_RL_container);
            this.category_IMG_icon = itemView.findViewById(R.id.category_IMG_icon);
            this.category_TV_title = itemView.findViewById(R.id.category_TV_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category category = getItem(getAdapterPosition());
                    for(Category c : categories){
                        c.setSelected(false);
                    }
                    category.setSelected(true);
                    categoryClickedListener.categoryItemClicked(category, getAdapterPosition());
                }
            });
        }
    }
}
