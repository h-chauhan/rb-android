package com.rajbhog.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajbhog.POJO.Category;
import com.rajbhog.ProductsListActivity;
import com.rajbhog.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter {
    List<Category> categories;
    private TextView header;

    public class CategoryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public CategoryViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            header = (TextView) itemView.findViewById(R.id.category_header);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductsListActivity.class);
            int i = getAdapterPosition();
            intent.putExtra("category", categories.get(i).getId());
            intent.putExtra("category_name", categories.get(i).getName());
            context.startActivity(intent);
        }
    }

    public CategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorylistitem, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        header.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
