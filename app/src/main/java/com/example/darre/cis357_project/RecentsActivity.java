package com.example.darre.cis357_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.darre.cis357_project.dummy.DummyContentRecents;

public class RecentsActivity extends AppCompatActivity
        implements RecentsFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent();
                setResult(MainActivity.RECENTS_RESULT,intent);
                finish();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(DummyContentRecents.DummyRecents item) {
        System.out.println("Interact!");
    }
}
