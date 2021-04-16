package com.eco.ecommerceapp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eco.ecommerceapp.Interface.ItmeClickListener;
import com.eco.ecommerceapp.R;

public class ProductViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productNames,productDescription,productPrice;
    public ImageView productImage;
    public ItmeClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        productNames=itemView.findViewById(R.id.product_name);
        productDescription=itemView.findViewById(R.id.product_description);
        productImage=itemView.findViewById(R.id.product_image);
        productPrice=itemView.findViewById(R.id.product_price);
    }


    public void setItemClickListener(ItmeClickListener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {

        listener.onClick(v,getAdapterPosition(),false);
    }
}
