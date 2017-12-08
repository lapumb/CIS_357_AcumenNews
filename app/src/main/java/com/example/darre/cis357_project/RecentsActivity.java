package com.example.darre.cis357_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class RecentsActivity extends AppCompatActivity
        implements RecentsFragment.OnListFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onListFragmentInteraction(NewsLookup article) {
        Intent intent = new Intent();
        String[] vals = {article.getTitle()};
        intent.putExtra("item", vals);
        setResult(MainActivity.RECENTS_RESULT,intent);
        finish();
    }
}
