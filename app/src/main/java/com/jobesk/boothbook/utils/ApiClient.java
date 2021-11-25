package com.jobesk.boothbook.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static final String BASE_URL="https://vendor.myboothbooking.com/api/v1/";

    private static Retrofit retrofit = null;
    private static Gson gson;

//    public static Retrofit getApiClient() {
//
//        if (retrofit==null)
//        {
//            gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//
//            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson)).
//                            addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//
//        return retrofit;
//    }


    public static Retrofit getApiClientWithoutUrl(String url) {

//        if (retrofit == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson)).
                            addConverterFactory(GsonConverterFactory.create())
                    .build();
//        }

        return retrofit;
    }
}
