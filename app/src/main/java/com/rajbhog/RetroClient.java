package com.rajbhog;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static String API_BASE_URL = "http://rajbhog.herokuapp.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(
                    GsonConverterFactory.create()
            );

    private static Retrofit retrofit = builder.client(httpClient.build()).build();

    static DataInterface client = retrofit.create(DataInterface.class);

}
