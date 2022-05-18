package com.example.immoloc.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.immoloc.database.AdTable;

public class AdsListAdapter extends ListAdapter<AdTable, AdsViewHolder> {

public AdsListAdapter(@NonNull DiffUtil.ItemCallback<AdTable> diffCallback) {
        super(diffCallback);
}

        @Override
public AdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdsViewHolder.create(parent);
        }

@Override
public void onBindViewHolder(AdsViewHolder holder, int position) {
        AdTable current = getItem(position);
        holder.bind("Prix du bien= "+String.valueOf(current.getPrice())+ "€");
        // à continuer pour les autres champs de l'annonce, à modifier pour l'user courant
        }

public static class WordDiff extends DiffUtil.ItemCallback<AdTable> {

        @Override
        public boolean areItemsTheSame(@NonNull AdTable oldItem, @NonNull AdTable newItem) {
                return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AdTable oldItem, @NonNull AdTable newItem) {
                return oldItem.getText().equals(newItem.getText());
        }
}

}