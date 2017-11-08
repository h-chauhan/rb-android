package com.rajbhog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rajbhog.Adapter.CartItemAdapter;
import com.rajbhog.POJO.Basket;

public class PostOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order);

        if(Basket.getItemCount() == 0)
            finish();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostOrderActivity.this, EnterInfoActivity.class);
                startActivity(intent);
            }
        });
        int subtotal = Basket.getTotalAmount();
        int tax = (18 * subtotal) / 100;
        int total = subtotal + tax;
        TextView subtotalView = (TextView) findViewById(R.id.subtotal);
        subtotalView.setText("Subtotal: ₹ " + String.valueOf(subtotal));
        TextView taxView = (TextView) findViewById(R.id.tax);
        taxView.setText("GST@18%: ₹ " + String.valueOf(tax));
        TextView totalTextView = (TextView) findViewById(R.id.total);
        totalTextView.setText("Total: ₹ " + String.valueOf(total));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cart_item_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new CartItemAdapter(this, subtotalView,
                taxView, totalTextView);
        recyclerView.setAdapter(adapter);
    }
}
