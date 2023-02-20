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
import com.project.stylebuyer.firebase.Order;
import com.project.stylebuyer.interfaces.OrderClickedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Order> orders;
    private Activity activity;
    private OrderClickedListener orderClickedListener;

    public OrderAdapter(Activity activity, ArrayList<Order> orders){
        this.orders = orders;
        this.activity = activity;
    }

    public void setOrderClickedListener(OrderClickedListener orderClickedListener){
        this.orderClickedListener = orderClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
       return new OrderAdapter.OrderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderAdapter.OrderViewHolder orderViewHolder = (OrderAdapter.OrderViewHolder) holder;
        Order order = getItem(position);

        DateFormat obj = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date time = new Date(order.getOrderTime());

        Glide.with(activity).load(order.getProduct().getImageUrl())
                .into(orderViewHolder.order_IMG_icon);
        orderViewHolder.order_TV_price.setText(order.getProduct().getPrice() + " ₪");
        orderViewHolder.order_TV_title.setText(order.getProduct().getName());
        orderViewHolder.order_TV_size.setText("Size: " +order.getSize());
        orderViewHolder.order_TV_time.setText(obj.format(time));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private Order getItem(int position) {
        return orders.get(position);
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public ImageView order_IMG_icon;
        public TextView order_TV_title;
        public TextView order_TV_price;
        public TextView order_TV_size;
        public TextView order_TV_time;
        public TextView order_TV_remove;

        public OrderViewHolder(final View itemView) {
            super(itemView);
            this.order_IMG_icon = itemView.findViewById(R.id.order_IMG_icon);
            this.order_TV_title = itemView.findViewById(R.id.order_TV_title);
            this.order_TV_price = itemView.findViewById(R.id.order_TV_price);
            this.order_TV_size = itemView.findViewById(R.id.order_TV_size);
            this.order_TV_time = itemView.findViewById(R.id.order_TV_time);
            this.order_TV_remove = itemView.findViewById(R.id.order_TV_remove);

            order_TV_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Order order = getItem(getAdapterPosition());
                    if(orderClickedListener != null){
                        orderClickedListener.orderItemClicked(order, getAdapterPosition());
                    }
                }
            });
        }
    }
}
