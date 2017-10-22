package com.rajbhog;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.rajbhog.POJO.Order;

public class EnterInfoActivity extends AppCompatActivity {

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((RadioButton) findViewById(R.id.hd)).isChecked()) {
                    order = new Order("Delivery");
                } else if (((RadioButton) findViewById(R.id.pu)).isChecked()) {
                    order = new Order("Pick Up");
                } else {
                    order = new Order("Book Table");
                }

                Intent intent = new Intent(EnterInfoActivity.this, CustomerOrderActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });
    }
}
