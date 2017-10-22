package com.rajbhog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajbhog.Adapter.ProductAdapter;
import com.rajbhog.POJO.Basket;
import com.rajbhog.POJO.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsListActivity extends AppCompatActivity {
    int categoryId;
    RelativeLayout basket;
    TextView basketCount;
    TextView basketAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        basket = (RelativeLayout) findViewById(R.id.basket);
        basketCount = (TextView) findViewById(R.id.basketCount);
        basketAmount = (TextView) findViewById(R.id.basketAmt);

        setBasket();

        final RecyclerView rvProducts = (RecyclerView) findViewById(R.id.productsList);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setHasFixedSize(true);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("category", -1);
        String categoryName = intent.getStringExtra("category_name");

        TextView tvCategory = (TextView) findViewById(R.id.product_category);
        tvCategory.setText(categoryName);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Menu");
        pd.show();
        pd.setCancelable(false);

        Call<List<Product>> callProducts = RetroClient.client.getProducts(categoryId);
        callProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call,
                                   @NonNull Response<List<Product>> response) {
                ProductAdapter adapter = new ProductAdapter(response.body());
                rvProducts.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {

            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PostOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setBasket();
    }


    private void setBasket() {
        if (Basket.getCount() > 0)
        {
            basket.setVisibility(View.VISIBLE);
            if (Basket.getItemCount() == 1) {
                basketCount.setText("1 ITEM IN CART");
            } else {
                basketCount.setText(Basket.getItemCount() + " ITEMS IN CART");
            }

            basketAmount.setText("₹ " + Basket.getTotalAmount());
        }
        else
            basket.setVisibility(View.GONE);
    }
}
