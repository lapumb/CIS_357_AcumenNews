package com.example.darre.cis357_project;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.darre.cis357_project.NewsFragment.OnListFragmentInteractionListener;
import com.example.darre.cis357_project.model.event_registry.SourceResult;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsLookup} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourceViewHolder> {

    private List<SourceResult> selectedSources;
    private List<SourceResult> visibleSources;
    private final SourcesActivity listener;
    private Context context;

    public SourcesAdapter(Context context, List<SourceResult> selectedSources, List<SourceResult> visibleSources, SourcesActivity listener) {
        this.context = context;
        this.selectedSources = selectedSources;
        this.visibleSources = visibleSources;
        this.listener = listener;
    }

    public void setSources(List<SourceResult> selectedSources, List<SourceResult> visibleSources) {
        this.selectedSources = selectedSources;
        this.visibleSources = visibleSources;
    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_source, parent, false);
        return new SourceViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final SourceViewHolder holder, int position) {
        SourceResult source = visibleSources.get(position);
        holder.source = source;
        holder.sourceName.setText(source.getTitle());
        holder.sourceUri.setText(source.getUri());
        holder.selected = isSelected(source);

        setIcon(holder);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onSourceListFragmentInteraction(holder.source);
                    holder.selected = !holder.selected;
                    setIcon(holder);
                }

            }
        };

        holder.view.setOnClickListener(clickListener);
        holder.button.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return visibleSources.size();
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        final TextView sourceName;
        final TextView sourceUri;
        final Button button;
        Boolean selected = false;
        SourceResult source;

        SourceViewHolder(View view) {
            super(view);
            this.view = view;
            sourceName = view.findViewById(R.id.source_name);
            sourceUri = view.findViewById(R.id.source_uri);
            button = view.findViewById(R.id.source_button);
        }

        // TODO: checkbox

        @Override
        public String toString() {
            return super.toString() + " '" + sourceName.getText() + "'";
        }
    }

    private Boolean isSelected(SourceResult source) {
        for (SourceResult next : selectedSources) {
            if (next.getTitle().equals(source.getTitle()) && next.getUri().equals(source.getUri())) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setIcon(SourceViewHolder holder) {
        if(holder.selected) {
            VectorDrawable bg = (VectorDrawable) context.getResources().getDrawable(R.drawable.ic_check_black_24dp);
            holder.button.setBackground(bg);
        } else {
            VectorDrawable bg = (VectorDrawable) context.getResources().getDrawable(R.drawable.ic_add_black_24dp);
            holder.button.setBackground(bg);
        }
    }
}
