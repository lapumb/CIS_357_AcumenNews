package com.example.darre.cis357_project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darre.cis357_project.NewsFragment.OnListFragmentInteractionListener;
import com.example.darre.cis357_project.model.event_registry.Article;

import org.w3c.dom.Text;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsLookup} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<Article> articles;
    private final OnListFragmentInteractionListener listener;

    public NewsAdapter(List<Article> events, OnListFragmentInteractionListener listener) {
        this.articles = events;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.article = articles.get(position);
        holder.eventTitle.setText(articles.get(position).getTitle());
        holder.eventSource.setText(articles.get(position).getSource().getTitle());
        holder.eventSummary.setText(articles.get(position).getBody());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.article);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView eventTitle;
        final TextView eventSource;
        final TextView eventSummary;
        Article article;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            eventTitle = view.findViewById(R.id.article_title);
            eventSource = view.findViewById(R.id.article_source);
            eventSummary = view.findViewById(R.id.article_summary);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + eventTitle.getText() + "'";
        }
    }
}
