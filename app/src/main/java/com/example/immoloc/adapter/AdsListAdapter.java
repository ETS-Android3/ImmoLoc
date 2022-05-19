package com.example.immoloc.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.example.immoloc.AddAd;
import com.example.immoloc.R;
import com.example.immoloc.database.AdTable;

import java.io.File;

public class AdsListAdapter extends ListAdapter<AdTable, AdsViewHolder> {

        public AddAd ajtAn;
        public ImageView imageView;
        public View view;

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
        //imageView = view.findViewById(R.id.imgMyAds);
        holder.bind("Annonce n° "+current.getId()+"\n"+"Prix du bien= "+String.valueOf(current.getPrice())+
                "€"+"\nby user:" +current.userId);
        //Uri uri = Uri.parse(ajtAn.uri.toString());
       // Glide.with(holder.itemView.getContext())
        //        .load(new File(uri.getPath()))
         //       .into(imageView);
        // à continuer pour les autres champs de l'annonce, à modifier pour l'user courant
        }

public static class AdDiff extends DiffUtil.ItemCallback<AdTable> {

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