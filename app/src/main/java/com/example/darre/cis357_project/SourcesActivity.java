package com.example.darre.cis357_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.darre.cis357_project.api.ApiClientBuilder;
import com.example.darre.cis357_project.api.EventsApiClient;
import com.example.darre.cis357_project.helper.Constants;
import com.example.darre.cis357_project.model.event_registry.SourceResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesActivity extends AppCompatActivity {
    private static final String TAG = NewsFragment.class.getSimpleName();

    private EventsApiClient eventsApiClient;
    private SourcesActivity activity = this;
    private SourcesAdapter adapter = null;

    private List<SourceResult> selectedSources = null;
    private List<SourceResult> visibleSources = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventsApiClient = (new ApiClientBuilder<>(EventsApiClient.class, Constants.API_BASE_URL)).build();

        setupAdapter();
        setupSearch();
    }

    public void onSourceListFragmentInteraction(SourceResult source) {
        Boolean found = false;
        Iterator<SourceResult> it = selectedSources.iterator();
        while (it.hasNext()) {
            SourceResult next = it.next();
            if (next.getTitle().equals(source.getTitle()) && next.getUri().equals(source.getUri())) {
                it.remove();
                //TODO update firebase
                found = true;
                break;
            }
        }
        if (!found) {
            selectedSources.add(source);
            //TODO: update firebase
        }
    }

    private List<SourceResult> getCurrentSources() {
        List<SourceResult> sources = new ArrayList<>(5);


        //TODO: actually get this from firebase
        sources.add(new SourceResult(0, "nytimes.com", "New York Times"));
        sources.add(new SourceResult(0, "washingtonpost.com", "Washington Post"));

        return sources;
    }

    private void setAdapter(SourcesAdapter adapter) {
        this.adapter = adapter;
    }

    private void setupAdapter() {
        View view = findViewById(R.id.sources_list);

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            selectedSources = getCurrentSources();
            visibleSources = new ArrayList<>(selectedSources);

            SourcesAdapter adapter = new SourcesAdapter(getApplicationContext(), selectedSources, visibleSources, activity);
            setAdapter(adapter);
            recyclerView.setAdapter(adapter);
        }
    }

    private void setupSearch() {
        View view = findViewById(R.id.sources_search);

        if (view instanceof EditText) {
            final EditText searchBar = (EditText) view;

            searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        if (in != null) {
                            in.hideSoftInputFromWindow(searchBar
                                            .getApplicationWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        search(searchBar.getText().toString());
                        handled = true;
                    }
                    return handled;
                }
            });

        }
    }

    private void updateView() {
        adapter.setSources(selectedSources, visibleSources);
        adapter.notifyDataSetChanged();
    }

    private void search(final String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            visibleSources = selectedSources;
            updateView();
        } else {
            Log.w("", "!!!!!!!!!!  Keyword: " + keyword);

            Map<String, String> queryParams = new HashMap<String, String>() {
                {
                    put("prefix", keyword);
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

                    visibleSources = response.body();
                    updateView();
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
}
