package com.example.darre.cis357_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.darre.cis357_project.model.event_registry.SourceResult;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BlacklistActivity extends AppCompatActivity {
    DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Blacklist");

        firebase = FirebaseDatabase.getInstance().getReference("blacklist").child(InstanceID.getInstance(getApplicationContext()).getId());

        loadBlacklist();

    }

    private void setViews(String blacklist) {
        final EditText field = (EditText) findViewById(R.id.blacklistText);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        field.setText(blacklist);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBlacklist(field.getText().toString());
                Snackbar.make(findViewById(android.R.id.content), "Blacklist Saved.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setBlacklist(String blacklist) {
        firebase.setValue(blacklist);
    }

    private void loadBlacklist() {
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String blacklist = (String) dataSnapshot.getValue();
                if(blacklist == null) {
                    blacklist = "";
                }

                setViews(blacklist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(findViewById(android.R.id.content), "Failed to load existing blacklist.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                setViews("");
            }
        });
    }
}
