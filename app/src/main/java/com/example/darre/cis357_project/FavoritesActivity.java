package com.example.darre.cis357_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity
        implements FavoritesFragment.OnListFragmentInteractionListener {

    DatabaseReference firebase;
    List<NewsLookup> allFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allFavorites = new ArrayList<NewsLookup>();



    }

    @Override
    public void onListFragmentInteraction(NewsLookup article) {
        System.out.println("Interact!");
    }

}
