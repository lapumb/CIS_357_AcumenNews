package com.example.darre.cis357_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.darre.cis357_project.api.ApiClientBuilder;
import com.example.darre.cis357_project.api.EventsApiClient;
import com.example.darre.cis357_project.helper.Constants;
import com.example.darre.cis357_project.model.event_registry.SourceResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesActivity extends AppCompatActivity {
    private static final String TAG = NewsFragment.class.getSimpleName();

    private EventsApiClient eventsApiClient;
    private SourcesActivity activity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventsApiClient = (new ApiClientBuilder<>(EventsApiClient.class, Constants.API_BASE_URL)).build();

        View view = findViewById(R.id.sources_list);

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            //prefix=new%20york&lang=eng
            Map<String, String> queryParams = new HashMap<String, String>()
            {
                {
                    put("prefix", "new york");
                    put("lang", "eng");
                    put("apiKey", Constants.API_KEY);
                }
            };

            eventsApiClient.getSources(queryParams).enqueue(new Callback<List<SourceResult>>() {
                @Override
                public void onResponse(@NonNull Call<List<SourceResult>> call, @NonNull Response<List<SourceResult>> response) {
                    Log.w(TAG, call.request().url().toString());
                    if (response.body() == null) {
                        Log.w(TAG, "Null Response");
                        Snackbar.make(getWindow().findViewById(android.R.id.content), "Failed to load sources.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        return;
                    }

                    Log.w(TAG, response.body().size() + " sources returned");

                    recyclerView.setAdapter(new SourcesAdapter(response.body(), activity));
                }

                @Override
                public void onFailure(@NonNull Call<List<SourceResult>> call, @NonNull Throwable t) {
                    Log.w(TAG, call.request().url().toString());
                    Snackbar.make(getWindow().findViewById(android.R.id.content), "Failed to load sources.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.w(TAG, t);
                }
            });
        }
    }

    public void onSourceListFragmentInteraction(SourceResult source) {

    }
}
