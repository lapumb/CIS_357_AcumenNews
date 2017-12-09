package com.example.darre.cis357_project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.darre.cis357_project.api.ApiClientBuilder;
import com.example.darre.cis357_project.api.EventsApiClient;
import com.example.darre.cis357_project.helper.Constants;
import com.example.darre.cis357_project.model.event_registry.SourceResult;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    DatabaseReference firebase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Sources");

        eventsApiClient = (new ApiClientBuilder<>(EventsApiClient.class, Constants.API_BASE_URL)).build();

        firebase = FirebaseDatabase.getInstance().getReference("sources").child(InstanceID.getInstance(getApplicationContext()).getId());

        setupAdapter();
        setupSearch();
    }

    public void onSourceListFragmentInteraction(SourceResult source) {
        Boolean found = false;
        Iterator<SourceResult> it = selectedSources.iterator();
        while (it.hasNext()) {
            SourceResult next = it.next();
            if (next.getUri().equals(source.getUri())) {
                it.remove();

                firebase.child(source.getUri().replaceAll("\\.", "\\%")).getRef().removeValue();

                found = true;
                break;
            }
        }
        if (!found) {
            selectedSources.add(source);
            firebase.child(source.getUri().replaceAll("\\.","\\%")).setValue(source);
        }
    }

    private List<SourceResult> getCurrentSources() {
        List<SourceResult> sources = new ArrayList<>(5);

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

            loadSources();
        }
    }

    private void setViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sources_list);
        SourcesAdapter adapter = new SourcesAdapter(getApplicationContext(), selectedSources, visibleSources, activity);
        setAdapter(adapter);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        View view = findViewById(R.id.sources_search);

        if (view instanceof EditText) {
            final EditText searchBar = (EditText) view;

            searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        searchStart(searchBar);
                        handled = true;
                    }
                    return handled;
                }
            });

            final Button searchButton = (Button) findViewById(R.id.searchButton);
            final Button cancelButton = (Button) findViewById(R.id.cancelButton);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchStart(searchBar);
                }
            });


            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchBar.setText("");
                    searchStart(searchBar);
                }
            });
        }
    }

    private void updateView() {
        adapter.setSources(selectedSources, visibleSources);
        adapter.notifyDataSetChanged();
    }

    private void searchStart(EditText searchBar) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (in != null) {
            in.hideSoftInputFromWindow(searchBar
                            .getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        search(searchBar.getText().toString());
    }

    private void search(final String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            visibleSources = selectedSources;
            updateView();
        } else {
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
                    if (response.body() == null) {
                        Snackbar.make(getWindow().findViewById(android.R.id.content), "Failed to load sources.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        return;
                    }

                    visibleSources = response.body();
                    updateView();
                }

                @Override
                public void onFailure(@NonNull Call<List<SourceResult>> call, @NonNull Throwable t) {
                    Snackbar.make(getWindow().findViewById(android.R.id.content), "Failed to load sources.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    public void loadSources() {
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                selectedSources = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if(dsp.getValue() != null) {
                        HashMap tmp = (HashMap) dsp.getValue();
                        SourceResult newSource = new SourceResult(0, (String) tmp.get("uri"), (String) tmp.get("title"));
                        selectedSources.add(newSource);
                    }
                }
                visibleSources = new ArrayList<>(selectedSources);

                setViews();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(findViewById(android.R.id.content), "Failed to load your sources.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                selectedSources = new ArrayList<>();
                visibleSources = new ArrayList<>();

                setViews();
            }
        });
    }
}
