package com.example.immoloc.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.example.immoloc.AddAd;
import com.example.immoloc.ModifyAdActivity;
import com.example.immoloc.R;
import com.example.immoloc.database.AdTable;

import java.io.File;

public class AdsListAdapter extends ListAdapter<AdTable, AdsViewHolder> {

        public ImageView imageView;
        SharedPreferences pref;
        //public String[] mColors = {"#3F51B5","#FF9800","#009688","#673AB7"};

public AdsListAdapter(@NonNull DiffUtil.ItemCallback<AdTable> diffCallback) {
        super(diffCallback);
}

        @Override
public AdsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                pref = parent.getContext().getSharedPreferences("hey", Context.MODE_PRIVATE);
                return AdsViewHolder.create(parent);
        }

@Override
public void onBindViewHolder(AdsViewHolder holder, int position) {
        AdTable current = getItem(position);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ad_id", String.valueOf(current.getId()));
        editor.commit();

        //holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 4]));
        holder.bind("Annonce n° "+current.getId()+"\n"+"Prix du bien= "+String.valueOf(current.getPrice())+
                "€"+"\nby user:" +current.userId);
        //holder.adItemView.setBackgroundResource(R.drawable.list_border);
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

public interface onAdListener{
        void onAdClick(int position);
}

}