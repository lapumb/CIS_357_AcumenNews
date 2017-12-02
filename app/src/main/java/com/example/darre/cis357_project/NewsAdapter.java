package com.example.darre.cis357_project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darre.cis357_project.NewsFragment.OnListFragmentInteractionListener;
import com.example.darre.cis357_project.dummy.DummyContentNews.DummyNews;
import com.example.darre.cis357_project.model.event_registry.Event;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyNews} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<Event> events;
    private final OnListFragmentInteractionListener listener;

    public NewsAdapter(List<Event> events, OnListFragmentInteractionListener listener) {
        this.events = events;
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
        holder.event = events.get(position);
        holder.eventTitle.setText(events.get(position).getTitle().getEng());
        holder.eventSummary.setText(events.get(position).getSummary().getEng());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.event);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView eventTitle;
        final TextView eventSummary;
        Event event;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            eventTitle = view.findViewById(R.id.event_title);
            eventSummary = view.findViewById(R.id.event_summary);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + eventTitle.getText() + "'";
        }
    }
}
