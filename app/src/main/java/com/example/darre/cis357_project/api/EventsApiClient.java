package com.example.darre.cis357_project.api;

import com.example.darre.cis357_project.model.event_registry.EventResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Andrew Prins on 11/30/2017.
 */

public interface EventsApiClient {

    @GET("json/event")
    Call<EventResponse> getEvents(@QueryMap Map<String, String> params);

    // TODO: Add one for articles
}
