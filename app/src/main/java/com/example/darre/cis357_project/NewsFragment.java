package com.example.darre.cis357_project;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.darre.cis357_project.api.ApiClientBuilder;
import com.example.darre.cis357_project.api.EventsApiClient;
import com.example.darre.cis357_project.helper.Constants;
import com.example.darre.cis357_project.helper.QueryBuilder;
import com.example.darre.cis357_project.model.event_registry.Article;
import com.example.darre.cis357_project.model.event_registry.ArticlesResponse;
import com.example.darre.cis357_project.model.event_registry.Source;
import com.example.darre.cis357_project.model.event_registry.SourceResult;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends Fragment {
    private static final String TAG = NewsFragment.class.getSimpleName();

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private EventsApiClient eventsApiClient;
    private String keyword = null;
    final ArrayList<SourceResult> sources = new ArrayList<>();
    DatabaseReference sourcesFirebase;
    DatabaseReference blacklistFirebase;
    String blacklist = null;

    RecyclerView recyclerView;
    EditText searchField;
    Button cancelButton;
    Button searchButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NewsFragment newInstance(int columnCount) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventsApiClient = (new ApiClientBuilder<>(EventsApiClient.class, Constants.API_BASE_URL)).build();
        sourcesFirebase = FirebaseDatabase.getInstance().getReference("sources").child(InstanceID.getInstance(getContext()).getId());
        blacklistFirebase = FirebaseDatabase.getInstance().getReference("blacklist").child(InstanceID.getInstance(getContext()).getId());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        searchField = view.findViewById(R.id.article_search);
        cancelButton = view.findViewById(R.id.cancelButton);
        searchButton = view.findViewById(R.id.searchButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        loadSources();

        setupSearch();

        return view;
    }

    public void onRefresh() {
        hideKeyboard();

        //&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=100&articlesIncludeArticleImage=true&articlesArticleBodyLen=-1
        Map<String, String> queryParams = new HashMap<String, String>()
        {
            {
                put("query", (new QueryBuilder().withSources(sources)).withKeyword(keyword).build());
                put("action", "getArticles");
                put("resultType", "articles");
                put("articlesSortBy", "date"); // "rel" or "date"
                put("articlesCount", "50");
                put("articlesIncludeArticleImage", "true");
                put("articlesArticleBodyLen", "-1");
                put("apiKey", Constants.API_KEY);
            }
        };

        eventsApiClient.getArticles(queryParams).enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticlesResponse> call, @NonNull Response<ArticlesResponse> response) {
                Log.w(TAG, call.request().url().toString());
                if (response.body() == null || response.body().getResult() == null) {
                    Log.w(TAG, "Null Response");
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Failed to load articles.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    return;
                }

                List<Article> articles = filterArticles(response.body().getResult().getArticles());


                recyclerView.setAdapter(new NewsAdapter(articles, mListener));
            }

            @Override
            public void onFailure(@NonNull Call<ArticlesResponse> call, @NonNull Throwable t) {
                Log.w(TAG, call.request().url().toString());
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Failed to load articles.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.w(TAG, t);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Article article);
    }

    public void loadSources() {
        sourcesFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if(dsp.getValue() != null) {
                        HashMap tmp = (HashMap) dsp.getValue();
                        SourceResult newSource = new SourceResult(0, (String) tmp.get("uri"), (String) tmp.get("title"));
                        sources.add(newSource);
                    }
                }
                loadBlacklist();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Failed to load your sources.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                loadBlacklist();
            }
        });
    }
    private void loadBlacklist() {
        blacklistFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                blacklist = (String) dataSnapshot.getValue();

                onRefresh();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Failed to load blacklist.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                onRefresh();
            }
        });
    }

    private List<Article> filterArticles(List<Article> articles) {
        List<Article> finalArticles = new ArrayList<>();
        if(blacklist == null) {
            blacklist = "";
        }
        String[] blacklistTerms = blacklist.split("\n");

        for(Article article: articles) {
            Boolean isOK = true;
            for(String term: blacklistTerms) {
                if(!term.trim().equals("") && (article.getTitle().toLowerCase().contains(term.toLowerCase()) || article.getBody().toLowerCase().contains(term.toLowerCase()))) {
                    isOK = false;
                    break;
                }
            }
            if(isOK) {
                finalArticles.add(article);
            }
        }
        return finalArticles;
    }

    public void setupSearch() {
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    keyword = searchField.getText().toString();
                    if(keyword.equals("")) {
                        keyword = null;
                    }
                    onRefresh();
                    handled = true;
                }
                return handled;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = searchField.getText().toString().trim();
                if(keyword.equals("")) {
                    keyword = null;
                }
                onRefresh();
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchField.setText("");
                keyword = null;
                onRefresh();
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (in != null) {
            in.hideSoftInputFromWindow(searchField
                            .getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
