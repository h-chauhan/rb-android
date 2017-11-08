package com.rajbhog.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajbhog.POJO.Basket;
import com.rajbhog.POJO.Product;
import com.rajbhog.PostOrderActivity;
import com.rajbhog.R;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemHolder> {
    private final TextView totalTextView;
    private final TextView subtotalView;
    private final TextView taxView;
    Product product;
    private TextView name, priceH, qtyH, amtH, priceF, qtyF, amtF;
    private Button minusBtnH, plusBtnH, minusBtnF, plusBtnF;
    private RelativeLayout layoutH;
    int intQtyH, intQtyF;
    private final Context context;

    public CartItemAdapter(Context context, TextView subtotalView,
                           TextView taxView, TextView totalTextView) {
        this.context = context;
        this.subtotalView = subtotalView;
        this.taxView = taxView;
        this.totalTextView = totalTextView;
    }

    @Override
    public CartItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartlistitem, parent, false);
        return new CartItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartItemHolder holder, final int position) {

        product = Basket.basket.get(position).getProduct();
        name.setText(product.getName());
        priceH.setText(" x ₹ " + product.getPriceh());
        priceF.setText(" x ₹ " + product.getPricef());

        intQtyH = Basket.getQuantityH(product);
        intQtyF = Basket.getQuantityF(product);
        setQtyAndAmt(intQtyH, intQtyF);

        if (product.getPriceh() == 0) {
            layoutH.setVisibility(View.GONE);
        }

        if(getItemCount() == 0) {
            ((AppCompatActivity)context).finish();
        }

        plusBtnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize(holder, position);
                intQtyH++;
                Basket.PlusBasketHalf(product, intQtyH);
                setQtyAndAmt(intQtyH, intQtyF);
            }
        });

        plusBtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize(holder, position);
                intQtyF++;
                Basket.PlusBasketFull(product, intQtyF);
                setQtyAndAmt(intQtyH, intQtyF);
            }
        });

        minusBtnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize(holder, position);
                if (intQtyH > 0) {
                    intQtyH--;
                    if (intQtyH == 0 && intQtyF == 0) {
                        Basket.RemoveFromBasket(product);
                        refresh();
                    } else {
                        Basket.MinusBasketHalf(product, intQtyH);
                    }
                    setQtyAndAmt(intQtyH, intQtyF);
                }
            }
        });

        minusBtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize(holder, position);
                if (intQtyF > 0) {
                    intQtyF--;
                    if (intQtyH == 0 && intQtyF == 0) {
                        Basket.RemoveFromBasket(product);
                        refresh();
                    } else {
                        Basket.MinusBasketFull(product, intQtyF);
                    }
                    setQtyAndAmt(intQtyH, intQtyF);
                }
            }
        });
    }

    private void setQtyAndAmt(int intQtyH , int intQtyF) {
        qtyH.setText(String.valueOf(intQtyH));
        qtyF.setText(String.valueOf(intQtyF));
        int intAmtH = intQtyH * product.getPriceh();
        int intAmtF = intQtyF * product.getPricef();
        amtH.setText("₹ " + String.valueOf(intAmtH));
        amtF.setText("₹ " + String.valueOf(intAmtF));
        totalTextView.setText("Total: ₹ " + String.valueOf(Basket.getTotalAmount()));
        int subtotal = Basket.getTotalAmount();
        int tax = (18 * subtotal) / 100;
        int total = subtotal + tax;
        subtotalView.setText("Subtotal: ₹ " + String.valueOf(subtotal));
        taxView.setText("GST@18%: ₹ " + String.valueOf(tax));
        totalTextView.setText("Total: ₹ " + String.valueOf(total));
    }

    private void initialize(RecyclerView.ViewHolder holder, int position) {
        product = Basket.basket.get(position).getProduct();
        intQtyH = Basket.getQuantityH(product);
        intQtyF = Basket.getQuantityF(product);

        qtyH = (TextView) holder.itemView.findViewById(R.id.rLiQtyH);
        amtH = (TextView) holder.itemView.findViewById(R.id.rItemAmtH);
        qtyF = (TextView) holder.itemView.findViewById(R.id.rLiQtyF);
        amtF = (TextView) holder.itemView.findViewById(R.id.rItemAmtF);
    }

    @Override
    public int getItemCount() {
        return Basket.getCount();
    }

    public void refresh() {
        Intent intent = new Intent(context, PostOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ((AppCompatActivity)context).finish();
        ((AppCompatActivity) context).overridePendingTransition(0, 0);
        context.startActivity(intent);
        ((AppCompatActivity) context).overridePendingTransition(0, 0);
    }

    class CartItemHolder extends RecyclerView.ViewHolder {
        public CartItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rName);

            priceH = (TextView) itemView.findViewById(R.id.rItemPriceH);

            priceF = (TextView) itemView.findViewById(R.id.rItemPriceF);

            qtyH = (TextView) itemView.findViewById(R.id.rLiQtyH);
            amtH = (TextView) itemView.findViewById(R.id.rItemAmtH);
            qtyF = (TextView) itemView.findViewById(R.id.rLiQtyF);
            amtF = (TextView) itemView.findViewById(R.id.rItemAmtF);

            minusBtnH = (Button) itemView.findViewById(R.id.rMinusBtnH);
            plusBtnH = (Button) itemView.findViewById(R.id.rPlusBtnH);
            minusBtnF = (Button) itemView.findViewById(R.id.rMinusBtnF);
            plusBtnF = (Button) itemView.findViewById(R.id.rPlusBtnF);

            layoutH = (RelativeLayout) itemView.findViewById(R.id.rLayoutH);
        }
    }
}
