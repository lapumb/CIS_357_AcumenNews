package com.example.darre.cis357_project.api;

/**
 * Created by Andrew Prins on 11/30/2017.
 */

import com.example.darre.cis357_project.helper.Constants;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiClientBuilder<T> {
    private final Class<T> serviceClass;

    public ApiClientBuilder(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public T build() {
        return new Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .client(buildHttpClient())
                .baseUrl(Constants.API_BASE_URL)
                .build()
                .create(serviceClass);
    }

    private OkHttpClient buildHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }
}