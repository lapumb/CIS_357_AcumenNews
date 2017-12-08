package com.example.darre.cis357_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darre.cis357_project.helper.DownloadImageTask;
import com.example.darre.cis357_project.model.event_registry.Article;

public class NewsViewActivity extends AppCompatActivity {

    Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        TextView title = (TextView) findViewById(R.id.news_name);
        TextView source = (TextView) findViewById(R.id.article_source);
        TextView content = (TextView) findViewById(R.id.article_body);
        final Button articleLink = (Button) findViewById(R.id.article_link);

        Intent i = this.getIntent();
        article = i.getParcelableExtra("article");

        title.setText(article.getTitle());
        source.setText(article.getSource().getTitle());
        content.setText(article.getBody());
        if(article.getImage() != null) {
            new DownloadImageTask((ImageView) findViewById(R.id.article_image))
                    .execute(article.getImage());
        }
        articleLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(article.getUrl()));
                startActivity(i);
            }
        });
    }
}
