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
    private final String baseUrl;

    public ApiClientBuilder(Class<T> serviceClass, String baseUrl) {
        this.serviceClass = serviceClass;
        this.baseUrl = baseUrl;
    }

    public T build() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(
                                MoshiConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        return retrofit.create(serviceClass);
    }

    private OkHttpClient buildHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }
}
