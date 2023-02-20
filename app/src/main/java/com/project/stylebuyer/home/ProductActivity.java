package com.project.stylebuyer.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;
import com.project.stylebuyer.R;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Order;
import com.project.stylebuyer.firebase.Product;

import java.util.Calendar;

public class ProductActivity extends AppCompatActivity {
    public static final String SMALL = "S";
    public static final String LARGE = "L";
    public static final String XLARGE = "XL";

    private ImageView product_IMG_productImage;
    private TextView product_TV_productTitle, product_TV_productPrice;
    private Button product_BTN_xlarge, product_BTN_large, product_BTN_small;
    private MaterialButton product_BTN_order;
    private String size;

    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        findViews();
        initVars();
    }

    private void initVars() {
        api = new Api();
        this.size = SMALL;
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        Glide.with(this).load(product.getImageUrl()).into(product_IMG_productImage);
        product_TV_productTitle.setText(product.getName());
        product_TV_productPrice.setText("Price: "+product.getPrice() + " ₪");

        product_BTN_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               product_BTN_small.setBackgroundColor(Color.parseColor("#F8D2F4"));
               product_BTN_large.setBackgroundColor(Color.parseColor("#CDCDCD"));
               product_BTN_xlarge.setBackgroundColor(Color.parseColor("#CDCDCD"));
               size = SMALL;
            }
        });
        product_BTN_large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_BTN_large.setBackgroundColor(Color.parseColor("#F8D2F4"));
                product_BTN_small.setBackgroundColor(Color.parseColor("#CDCDCD"));
                product_BTN_xlarge.setBackgroundColor(Color.parseColor("#CDCDCD"));
                size = LARGE;
            }
        });

        product_BTN_xlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product_BTN_xlarge.setBackgroundColor(Color.parseColor("#F8D2F4"));
                product_BTN_small.setBackgroundColor(Color.parseColor("#CDCDCD"));
                product_BTN_large.setBackgroundColor(Color.parseColor("#CDCDCD"));
                size = XLARGE;
            }
        });


        product_BTN_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser firebaseUser = api.getCurrentUser();
                Calendar c = Calendar.getInstance();

                Order order = new Order()
                       .setProduct(product)
                       .setClientUid(firebaseUser.getUid())
                       .setSize(size)
                       .setOrderTime(c.getTimeInMillis());

                api.addOrder(order);
                finish();
            }
        });
    }

    private void findViews() {
        product_IMG_productImage = findViewById(R.id.product_IMG_productImage);
        product_TV_productTitle = findViewById(R.id.product_TV_productTitle);
        product_TV_productPrice = findViewById(R.id.product_TV_productPrice);
        product_BTN_order = findViewById(R.id.product_BTN_order);
        product_BTN_xlarge = findViewById(R.id.product_BTN_xlarge);
        product_BTN_large = findViewById(R.id.product_BTN_large);
        product_BTN_small = findViewById(R.id.product_BTN_small);
    }
}