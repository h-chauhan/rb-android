package com.rajbhog.POJO;

import java.util.ArrayList;
import java.util.List;

public class Basket
{
    public static List<Item> basket = new ArrayList<>();

    private static int Contains(Product product) {
        for (int i = 0; i < getCount(); i++) {
            if ((basket.get(i)).eqls(product)) {
                return i;
            }
        }
        return -1;
    }

    public static int Contains(Item item) {
        for(int i = 0; i < getCount(); i++){
            if (((Item)basket.get(i)).eqls(item)) {
                return i;
            }
        }
        return -1;
    }

    public static void PlusBasketHalf(Product product, int quantityH) {
        int i = Contains(product);
        if (i == -1)
        {
            basket.add(new Item(product, quantityH, 0));
        } else
            setNewHalfQty(i, quantityH);
    }

    public static void PlusBasketFull(Product product, int quantityF) {
        int i = Contains(product);
        if (i == -1)
        {
            basket.add(new Item(product, 0, quantityF));
        } else
            setNewFullQty(i, quantityF);
    }


    public static void MinusBasketHalf(Product product, int quantityH) {
        int i = Contains(product);
        if (i != -1) {
            setNewHalfQty(i, quantityH);
        }
    }

    public static void MinusBasketFull(Product product, int quantityF) {
        int i = Contains(product);
        if (i != -1) {
            setNewFullQty(i, quantityF);
        }
    }

    public static void RemoveFromBasket(Product product) {
        int i = Contains(product);
        if(i != -1) {
            basket.remove(i);
        }
    }


    public static int getCount() {
        return basket != null ? basket.size() : 0;
    }

    public static int getItemCount() {
        int sum = 0;
        for (Item item:
                basket) {
            sum += item.getQuantityh() + item.getQuantityf();
        }
        return sum;
    }



    public static int getAmount(int i) {
        Product p = basket.get(i).getProduct();
        int qtyF = basket.get(i).getQuantityf();
        int qtyH = basket.get(i).getQuantityh();
        return (p.getPriceh() * qtyH) + (p.getPricef() * qtyF);
    }

    public static int getTotalAmount() {
        int sum = 0;
        for (Item item :
                basket) {
            sum += (item.getProduct().getPriceh() * item.getQuantityh())
                    + (item.getProduct().getPricef() * item.getQuantityf());
        }
        return sum;
    }


    public static int getQuantityH(Product product) {
        int i = Contains(product);
        if(i != -1) {
            return basket.get(i).getQuantityh();
        } else {
            return 0;
        }
    }

    public static int getQuantityF(Product product) {
        int i = Contains(product);
        if(i != -1) {
            return basket.get(i).getQuantityf();
        } else {
            return 0;
        }
    }


    private static void setNewQty(int i, int quantityH, int quantityF) {
        basket.get(i).setQuantityh(quantityH);
        basket.get(i).setQuantityf(quantityF);
    }

    private static void setNewHalfQty(int i, int quantityH) {
        basket.get(i).setQuantityh(quantityH);
    }

    private static void setNewFullQty(int i, int quantityF) {
        basket.get(i).setQuantityf(quantityF);
    }
}

