package com.example.darre.cis357_project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darre.cis357_project.NewsFragment.OnListFragmentInteractionListener;
import com.example.darre.cis357_project.dummy.DummyContentNews.DummyNews;
import com.example.darre.cis357_project.model.event_registry.Article;
import com.example.darre.cis357_project.model.event_registry.SourceResult;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyNews} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourceViewHolder> {

    private final List<SourceResult> sources;
    private final SourcesActivity listener;

    public SourcesAdapter(List<SourceResult> sources, SourcesActivity listener) {
        this.sources = sources;
        this.listener = listener;
    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_source, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SourceViewHolder holder, int position) {
        holder.source = sources.get(position);
        holder.sourceName.setText(sources.get(position).getTitle());
        holder.sourceUri.setText(sources.get(position).getUri());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onSourceListFragmentInteraction(holder.source);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView sourceName;
        final TextView sourceUri;
        SourceResult source;

        SourceViewHolder(View view) {
            super(view);
            this.view = view;
            sourceName = view.findViewById(R.id.source_name);
            sourceUri = view.findViewById(R.id.source_uri);
        }

        // TODO: checkbox

        @Override
        public String toString() {
            return super.toString() + " '" + sourceName.getText() + "'";
        }
    }
}
