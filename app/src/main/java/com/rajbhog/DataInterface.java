package com.rajbhog;

import java.util.List;

import com.rajbhog.POJO.Category;
import com.rajbhog.POJO.Item;
import com.rajbhog.POJO.Order;
import com.rajbhog.POJO.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface DataInterface {
    @GET("/get/category")
    Call<List<Category>> getCategories();

    @GET("/get/product/{id}/")
    Call<List<Product>> getProducts(@Path("id") int id);

    @POST("/get/order/")
    Call<Order> postOrder(@Body Order order);

    @POST("/get/item/")
    Call<Item> postItems(@Body Item item);
}
