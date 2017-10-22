package com.rajbhog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rajbhog.Adapter.CategoriesAdapter;
import com.rajbhog.POJO.Basket;
import com.rajbhog.POJO.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout basket;
    private TextView basketCount;
    private TextView basketAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check for internet connectivity.
        if(isNetworkAvailable()) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Loading Menu");
            pd.show();
            pd.setCancelable(false);

            final RecyclerView rvCategory = (RecyclerView) findViewById(R.id.categoryList);
            rvCategory.setLayoutManager(new LinearLayoutManager(this));
            rvCategory.setHasFixedSize(true);

            Call<List<Category>> getCategories = RetroClient.client.getCategories();
            getCategories.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(@NonNull Call<List<Category>> call,
                                       @NonNull Response<List<Category>> response) {
                    CategoriesAdapter adapter = new CategoriesAdapter(response.body());
                    rvCategory.setAdapter(adapter);
                    pd.dismiss();
                }

                @Override
                public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {

                }
            });
        } else {
            //Do Something when internet not connected.
            Toast.makeText(this, "No internet connection",
                    Toast.LENGTH_LONG).show();
        }

        //Basket components.
        basket = (RelativeLayout) findViewById(R.id.basket);
        basketCount = (TextView) findViewById(R.id.basketCount);
        basketAmount = (TextView) findViewById(R.id.basketAmt);

        //Next activity called on tapping on basket.
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PostOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    //To check internet connectivity.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Sets the component values to basket.
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

    //Set basket every time activity is restarted or resumed.
    @Override
    protected void onRestart() {
        super.onRestart();
        setBasket();
    }
}
