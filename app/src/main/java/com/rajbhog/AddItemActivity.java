package com.rajbhog;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajbhog.POJO.Basket;
import com.rajbhog.POJO.Product;

public class AddItemActivity extends AppCompatActivity {

    private Product product;
    private int intQtyH;
    private int intQtyF;
    private TextView qtyH;
    private TextView qtyF;
    private TextView amtH;
    private TextView amtF;
    private TextView amtT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        TextView name = (TextView) findViewById(R.id.iName);

        TextView priceH = (TextView) findViewById(R.id.itemPriceH);
        qtyH = (TextView) findViewById(R.id.iLiQtyH);
        amtH = (TextView) findViewById(R.id.itemAmtH);

        TextView priceF = (TextView) findViewById(R.id.itemPriceF);
        qtyF = (TextView) findViewById(R.id.iLiQtyF);
        amtF = (TextView) findViewById(R.id.itemAmtF);

        amtT = (TextView) findViewById(R.id.totAmt);

        Button minusBtnH = (Button) findViewById(R.id.iMinusBtnH);
        Button plusBtnH = (Button) findViewById(R.id.iPlusBtnH);
        Button minusBtnF = (Button) findViewById(R.id.iMinusBtnF);
        Button plusBtnF = (Button) findViewById(R.id.iPlusBtnF);

        RelativeLayout layoutH = (RelativeLayout) findViewById(R.id.iLayoutH);

        intQtyH = Basket.getQuantityH(product);
        intQtyF = Basket.getQuantityF(product);

        name.setText(product.getName());
        priceH.setText(" x ₹ " + product.getPriceh());
        priceF.setText(" x ₹ " + product.getPricef());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        setQtyAndAmt();

        if (product.getPriceh() == 0) {
            layoutH.setVisibility(View.GONE);
        }

        plusBtnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intQtyH++;
                Basket.PlusBasketHalf(product, intQtyH);
                setQtyAndAmt();
            }
        });

        plusBtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intQtyF++;
                Basket.PlusBasketFull(product, intQtyF);
                setQtyAndAmt();
            }
        });

        minusBtnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intQtyH > 0) {
                    intQtyH--;
                    if(intQtyH == 0 && intQtyF == 0) {
                        Basket.RemoveFromBasket(product);
                    } else {
                        Basket.MinusBasketHalf(product, intQtyH);
                    }
                    setQtyAndAmt();
                }
            }
        });

        minusBtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intQtyF > 0) {
                    intQtyF--;
                    if(intQtyH == 0 && intQtyF == 0) {
                        Basket.RemoveFromBasket(product);
                    } else {
                        Basket.MinusBasketFull(product, intQtyF);
                    }
                    setQtyAndAmt();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setQtyAndAmt() {
        qtyH.setText(String.valueOf(intQtyH));
        qtyF.setText(String.valueOf(intQtyF));
        int intAmtH = intQtyH * product.getPriceh();
        int intAmtF = intQtyF * product.getPricef();
        amtH.setText("₹ " + String.valueOf(intAmtH));
        amtF.setText("₹ " + String.valueOf(intAmtF));
        amtT.setText("₹ " + String.valueOf(intAmtH + intAmtF));
    }
}
