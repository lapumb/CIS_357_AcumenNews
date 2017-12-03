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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darre.cis357_project.api.ApiClientBuilder;
import com.example.darre.cis357_project.api.EventsApiClient;
import com.example.darre.cis357_project.helper.Constants;
import com.example.darre.cis357_project.helper.QueryBuilder;
import com.example.darre.cis357_project.model.event_registry.Article;
import com.example.darre.cis357_project.model.event_registry.ArticlesResponse;

import java.util.ArrayList;
import java.util.HashMap;
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

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            final ArrayList<String> sources = new ArrayList<>();
            sources.add("nytimes.com");
            sources.add("washingtonpost.com");

            //&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=100&articlesIncludeArticleImage=true&articlesArticleBodyLen=-1
            Map<String, String> queryParams = new HashMap<String, String>()
            {
                {
                    put("query", (new QueryBuilder().withSources(sources)).withKeyword(null).build());
                    put("action", "getArticles");
                    put("resultType", "articles");
                    put("articlesSortBy", "date"); // "rel" or "date"
                    put("articlesCount", "50");
                    put("articlesIncludeArticleImage", "true");
                    put("articlesArticleBodyLen", "-1");
                    put("apiKey", Constants.API_KEY);
                }
            };

            Log.w(TAG, queryParams.get("query"));

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

                    Log.w(TAG, response.body().getResult().getArticles().size() + " articles returned");

                    recyclerView.setAdapter(new NewsAdapter(response.body().getResult().getArticles(), mListener));
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
        return view;
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
}
