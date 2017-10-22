package com.rajbhog.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajbhog.AddItemActivity;
import com.rajbhog.POJO.Product;
import com.rajbhog.R;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    TextView name, priceh, pricef;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.productlistitem_withhalf, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.productlistitem, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        name = (TextView) holder.itemView.findViewById(R.id.liName);
        pricef = (TextView) holder.itemView.findViewById(R.id.liPriceF);
        Product product = products.get(position);
        if(getItemViewType(position) == 0) {
            priceh = (TextView) holder.itemView.findViewById(R.id.liPriceH);
            priceh.setText("Half: ₹ " + product.getPriceh());
            pricef.setText("Full: ₹ " + product.getPricef());
        } else {
            pricef.setText("₹ " + product.getPricef());
        }
        name.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(products.get(position).getPriceh() != 0)
            return 0;
        else
            return 1;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, AddItemActivity.class);
            int i = getAdapterPosition();
            intent.putExtra("product", (Serializable) products.get(i));
            context.startActivity(intent);
        }
    }

}
