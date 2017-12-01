package com.example.darre.cis357_project.api;

import com.example.darre.cis357_project.model.event_registry.EventResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Andrew Prins on 11/30/2017.
 */

public interface EventsApiClient {

    @GET("?{query}&action=getEvents&resultType=events&eventsSortBy={sort}&eventsCount={count}&eventsEventImageCount=1&eventsStoryImageCount=1&callback=JSON_CALLBACK")
    Call<EventResponse> getEvents(@Path("query") String query, @Path("sort") String sort, @Path("count") Integer count);

    // TODO: Add one for articles
}
