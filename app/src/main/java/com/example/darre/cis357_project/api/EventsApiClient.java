package com.example.darre.cis357_project.api;

import com.example.darre.cis357_project.model.event_registry.ArticlesResponse;
import com.example.darre.cis357_project.model.event_registry.SourceResult;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Andrew Prins on 11/30/2017.
 */

public interface EventsApiClient {

    @GET("json/article")
    Call<ArticlesResponse> getArticles(@QueryMap Map<String, String> params);

    @GET("json/suggestSources")
    Call<List<SourceResult>> getSources(@QueryMap Map<String, String> params);

    // TODO: Add one for articles
}
