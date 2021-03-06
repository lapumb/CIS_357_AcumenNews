package com.example.darre.cis357_project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darre.cis357_project.RecentsFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsLookup} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {

    private final List<NewsLookup> mValues;
    private final OnListFragmentInteractionListener mListener;

    public RecentsAdapter(List<NewsLookup> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(holder.mItem.title);
        holder.mText.setText(holder.mItem.text);
        holder.mDateTime.setText(holder.mItem.timestamp);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mText;
        public NewsLookup mItem;
        public final TextView mDateTime;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.article_title);
            mText = (TextView) view.findViewById(R.id.article_summary);
            mDateTime = (TextView) view.findViewById(R.id.article_source);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateTime.getText() + "'";
        }
    }
}
