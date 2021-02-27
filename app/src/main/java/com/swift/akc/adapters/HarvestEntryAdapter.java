package com.swift.akc.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.swift.akc.R;

public class HarvestEntryAdapter extends RecyclerView.Adapter<HarvestEntryAdapter.HarvestEntryViewHolder> {

    public static final String TAG = "MyOrderStoreListAdapter";

    private Activity mContext;

    public HarvestEntryAdapter(Activity context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public HarvestEntryAdapter.HarvestEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_harvest_entry, viewGroup, false);
        return new HarvestEntryAdapter.HarvestEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestEntryAdapter.HarvestEntryViewHolder holder, int position) {
        holder.itemEntry.setText("Hello World");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class HarvestEntryViewHolder extends RecyclerView.ViewHolder {

        TextView itemEntry;

        public HarvestEntryViewHolder(View view) {
            super(view);
            itemEntry = view.findViewById(R.id.item_entry);
        }
    }
}
