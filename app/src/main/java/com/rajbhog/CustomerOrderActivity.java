package com.rajbhog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rajbhog.POJO.Basket;
import com.rajbhog.POJO.Item;
import com.rajbhog.POJO.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);

        Intent intent = getIntent();
        final Order order = (Order) intent.getSerializableExtra("order");

        if (order.getType().equals("Delivery")) {
            findViewById(R.id.useraddress).setVisibility(View.VISIBLE);
        }

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab4);
        fab.setEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setEnabled(false);
                String str1 = ((TextView) findViewById(R.id.username)).
                        getText().toString();
                String str2 = ((TextView) findViewById(R.id.userphone)).
                        getText().toString();
                String str3 = ((TextView) findViewById(R.id.useraddress))
                        .getText().toString();
                if (str1.equals("")) {
                    ((TextView) findViewById(R.id.username))
                            .setError("Name is required!");
                } else if (str2.equals("")) {
                    ((TextView) findViewById(R.id.userphone))
                            .setError("Phone is required!");
                } else {
                    order.setUsername(str1);
                    order.setPhone(Long.parseLong(str2));
                    order.setAmount(Basket.getTotalAmount());

                    if(order.getType().equals("Delivery") && str3.equals("")) {
                        ((TextView) findViewById(R.id.useraddress))
                                .setError("Address is required!");
                    } else {
                        order.setAddress(str3.equals("") ? order.getType() : str3);

                        final ProgressDialog pd = new ProgressDialog(CustomerOrderActivity.this);
                        pd.setMessage("Sending your order");
                        pd.show();
                        pd.setCancelable(false);

                        Call<Order> postorder = RetroClient.client.postOrder(order);
                        postorder.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                Order createdOrder = response.body();
                                for(Item item: Basket.basket) {
                                    item.setOrder(createdOrder.getId());
                                    Call<Item> postitem = RetroClient.client.postItems(item);
                                    postitem.enqueue(new Callback<Item>() {
                                        @Override
                                        public void onResponse(Call<Item> call,
                                                               Response<Item> response) {
                                            Log.d("Item Post",
                                                    String.valueOf(response.body()));
                                            Log.d("Item Post Error",
                                                    String.valueOf(response.errorBody()));
                                        }

                                        @Override
                                        public void onFailure(Call<Item> call, Throwable t) {

                                        }
                                    });
                                }
                                pd.dismiss();
                                AlertDialog alertDialog = new AlertDialog
                                        .Builder(CustomerOrderActivity.this)
                                        .create();
                                alertDialog.setTitle("Success");
                                alertDialog.setMessage("You will soon receive a confirmation.");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                        new DialogInterface.OnClickListener() {
                                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Basket.basket.clear();
                                                finish();
                                                Intent intent = new Intent(
                                                        CustomerOrderActivity.this,
                                                        MainActivity.class);

                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
                                        });
                                alertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });
    }
}
