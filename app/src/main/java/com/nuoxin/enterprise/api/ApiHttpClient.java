package com.nuoxin.enterprise.api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nuoxin.enterprise.AppContext;
import com.nuoxin.enterprise.util.TLog;

import java.util.Locale;


/**
 * Created by lizhixian on 16/2/28.
 */
public class ApiHttpClient {
    public final static String HOST = "http://203.130.41.108:8083";
    //private static String API_URL = "http://nuoxin-enterprise-app-backend-dev.obaymax.com/%s";
    private static String API_URL = "http://nuoxin-enterprise-app-backend-test.obaymax.com/%s";
    //private static String API_URL = "http://eapp.airclass.com.cn/%s";
    public static final String GET = "GET";
    public static final String POST = "POST";

    public static AsyncHttpClient client;

    public ApiHttpClient() {
    }

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        log(getAbsoluteApiUrl(partUrl));
    }

    public static void get(String partUrl, RequestParams params,
                           AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("GET ").append(getAbsoluteApiUrl(partUrl)).append("&")
                .append(params).toString());
    }

    public static void post(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), handler);
        log(new StringBuilder("POST ").append(partUrl).toString());
    }

    public static void post(String partUrl, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), params, handler);
        log(new StringBuilder("POST ").append(partUrl).append("&")
                .append(params).toString());
    }

    public static void log(String log) {
        TLog.log("BaseApi", log);
    }

    public static void setHttpClient(AsyncHttpClient httpClient) {
        client = httpClient;
        //client.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    }

    public static void setTokenHeader(String token) {
        client.addHeader("X-Access-Token", token);
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!partUrl.startsWith("http:") && !partUrl.startsWith("https:")) {
            url = String.format(API_URL, partUrl);
        }
        Log.d("BASE_CLIENT", "request:" + url);
        return url;
    }
}
