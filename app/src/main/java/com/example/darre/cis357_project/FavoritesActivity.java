package com.example.darre.cis357_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.darre.cis357_project.dummy.DummyContent;

public class FavoritesActivity extends AppCompatActivity implements FavoritesFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        System.out.println("Interact!");
    }

}
