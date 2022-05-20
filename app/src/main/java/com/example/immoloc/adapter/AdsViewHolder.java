package com.example.immoloc.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.immoloc.ModifyAdActivity;
import com.example.immoloc.R;

public class AdsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView adItemView;
    public ImageView modifyMyAd;


    private AdsViewHolder(View itemView) {
        super(itemView);
        adItemView = itemView.findViewById(R.id.textView);
        modifyMyAd = itemView.findViewById(R.id.modifyAd);
        // écouteur sur le bouton de modification (crayon)
        modifyMyAd.setOnClickListener(this);
    }

    public void bind(String text) {
        adItemView.setText(text);
    }

    static AdsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_ad, parent, false);
        return new AdsViewHolder(view);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ModifyAdActivity.class);
        view.getContext().startActivity(intent);
    }
}
