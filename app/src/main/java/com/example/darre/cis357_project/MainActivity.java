package com.example.darre.cis357_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.darre.cis357_project.model.event_registry.Article;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsFragment.OnListFragmentInteractionListener {


//    public static int SETTINGS_RESULT = 1;
    public static int FAVORITES_RESULT = 2;
    public static int RECENTS_RESULT = 3;
    public static int BLACKLIST_RESULT = 4;
    public static int SOURCES_RESULT = 5;

    DatabaseReference topRef;
    public static List<NewsLookup> allRecents;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        allRecents = new ArrayList<NewsLookup>();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_settings) {
//            Intent intent = new Intent(MainActivity.this, SourcesActivity.class);
//            startActivityForResult(intent, SETTINGS_RESULT );
//            return true;

        /*} else*/ if (id == R.id.nav_favs) {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivityForResult(intent, FAVORITES_RESULT );
            return true;

        } else if (id == R.id.nav_recents) {
            Intent intent = new Intent(MainActivity.this, RecentsActivity.class);
            startActivityForResult(intent, RECENTS_RESULT );
            return true;

        } else if (id == R.id.nav_sources) {
            Intent intent = new Intent(MainActivity.this, SourcesActivity.class);
            startActivityForResult(intent, SOURCES_RESULT );
            return true;

        } else if (id == R.id.nav_blacklist) {
            Intent intent = new Intent(MainActivity.this, SourcesActivity.class);
            startActivityForResult(intent, BLACKLIST_RESULT );
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onListFragmentInteraction(Article article) {
        Intent intent = new Intent(this, NewsViewActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);

        NewsLookup entry = new NewsLookup();
        entry.setTitle(article.getTitle());
        entry.setText(article.getBody());
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        entry.setTimestamp(fmt.print(DateTime.now()));
        topRef.push().setValue(entry);
    }

    @Override
    public void onResume(){
        super.onResume();
        allRecents.clear();
        topRef = FirebaseDatabase.getInstance().getReference("recents");
        topRef.addChildEventListener (chEvListener);
        //topRef.addValueEventListener(valEvListener);
    }

    @Override
    public void onPause(){
        super.onPause();
        topRef.removeEventListener(chEvListener);
    }


    private ChildEventListener chEvListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            NewsLookup entry = (NewsLookup) dataSnapshot.getValue(NewsLookup.class);
            entry._key = dataSnapshot.getKey();
            allRecents.add(entry);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            NewsLookup entry = (NewsLookup) dataSnapshot.getValue(NewsLookup.class);
            List<NewsLookup> newRecents = new ArrayList<NewsLookup>();
            for (NewsLookup t : allRecents) {
                if (!t._key.equals(dataSnapshot.getKey())) {
                    newRecents.add(t);
                }
            }
            allRecents = newRecents;
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}