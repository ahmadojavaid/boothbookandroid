package com.jobesk.boothbook.utils;

import android.content.Context;
import android.util.Log;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.entity.StringEntity;


public class WebReq {

    public static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
    }

//    public static void getWithBaseUrl(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
//        client.get(context, getAbsoluteUrl(url), params, responseHandler);
//        Log.d("urlHit",getAbsoluteUrl(url));
//    }
//
//    public static void postWithBaseUrl(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
//        client.post(context, getAbsoluteUrl(url), params, responseHandler);
//        Log.d("urlHit",getAbsoluteUrl(url));
//    }
    public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(context,url, params, responseHandler);
        Log.d("urlHit",url);
    }

    public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.post(context, url, params, responseHandler);
        Log.d("urlHit",url);
    }

//
//    private static String getAbsoluteUrl(String relativeUrl) {
//        return Urls.BASEURL + relativeUrl;
//    }

//
//    public static void post(Context context, String url, StringEntity entity, String json, ResponseHandlerInterface responseHandler) {
//        client.post(context, getAbsoluteUrl(url), entity, json, responseHandler);
//    }

}
