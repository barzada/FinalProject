package com.project.stylebuyer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.stylebuyer.R;
import com.project.stylebuyer.firebase.Product;
import com.project.stylebuyer.interfaces.ProductClickedListener;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<Product> products;
    private ProductClickedListener productClickedListener;

    public ProductAdapter(Activity activity, ArrayList<Product> products){
        this.activity = activity;
        this.products = products;
    }

    public void setProducts(ArrayList<Product> products){
        this.products = products;
    }

    public ProductAdapter setProductClickedListener(ProductClickedListener productClickedListener) {
        this.productClickedListener = productClickedListener;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        Product product = getItem(position);

        Glide.with(activity).load(product.getImageUrl()).into(productViewHolder.product_IMG_icon);
        productViewHolder.product_TV_price.setText(product.getPrice() + " ₪");
        productViewHolder.product_TV_title.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private Product getItem(int position) {
        return products.get(position);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView product_IMG_icon;
        public TextView product_TV_title;
        public TextView product_TV_price;

        public ProductViewHolder(final View itemView) {
            super(itemView);
            this.product_IMG_icon = itemView.findViewById(R.id.product_IMG_icon);
            this.product_TV_title = itemView.findViewById(R.id.product_TV_title);
            this.product_TV_price = itemView.findViewById(R.id.product_TV_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = getItem(getAdapterPosition());
                    if(productClickedListener != null)
                        productClickedListener.productItemClicked(product, getAdapterPosition());
                }
            });
        }
    }
}
