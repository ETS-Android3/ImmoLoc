package com.example.immoloc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.immoloc.R;

public class AdsViewHolder extends RecyclerView.ViewHolder {

    private final TextView adItemView;

    private AdsViewHolder(View itemView) {
        super(itemView);
        adItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        adItemView.setText(text);
    }

    static AdsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_ad, parent, false);
        return new AdsViewHolder(view);
    }
}
