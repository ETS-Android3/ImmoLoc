package com.example.immoloc.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.immoloc.DataConverter;
import com.example.immoloc.DetailsAdActivity;
import com.example.immoloc.ModifyAdActivity;
import com.example.immoloc.R;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.ImageDao;
import com.example.immoloc.database.ImageTable;

import java.io.File;
import java.util.List;

// CLASSE ADAPTER
public class AdsListAdapter extends ListAdapter<AdTable, AdsListAdapter.AdsViewHolder> {

        SharedPreferences pref;
        public ImageView modifyMyAd;
        public List<AdTable> myAds;
        public List<ImageTable> myImgs;
        public String[] mColors = {"#e5dcd6","#fff5ee"};
        private int selectedPos = RecyclerView.NO_POSITION;
        public byte[] myImg;
        public ImageDao imgDao;
        AppDatabase locImmoDatabase;

        public AdsListAdapter(@NonNull DiffUtil.ItemCallback<AdTable> diffCallback, List<AdTable> data, List<ImageTable> dataimg) {
        super(diffCallback);
        this.myAds = data;
        this.myImgs = dataimg;
}

        @Override
public AdsViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_ad, parent, false);
                pref = PreferenceManager.getDefaultSharedPreferences(parent.getContext());
                modifyMyAd = parent.findViewById(R.id.modifyAd);
                return new AdsViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {

        AdTable ads = myAds.get(position);
        holder.ad = ads; // annonce courante (très important)
        AdTable current = getItem(position);
        holder.bind("Annonce n° " + current.getId() + "\n" + "Prix du bien= " + String.valueOf(current.getPrice()) +
                "€" + "\nby user:" + current.userId); // à supr currentuserid

        // alterner avec les couleurs mColors le background des annonces
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
        // lorsqu'un item sera selectionné on changera le background momentanément
        holder.itemView.setSelected(selectedPos == position);



        ImageTable images = (ImageTable) myImgs.get(position);
        holder.imageAd.setImageBitmap(DataConverter.convertByteArray2Img(images.getImage()));
        //byte[] compressedImg = DataConverter.imageResize(images.getImage());
        images.setImage(images.getImage());


        //holder.adItemView.setBackgroundResource(R.drawable.list_border);
        /* Uri uri = Uri.parse(ajtAn.uri.toString()); //pour img to path
           Glide.with(holder.itemView.getContext())
                .load(new File(uri.getPath()))
               .into(imageView); */

          // mettre le byte[]
       /* Glide.with(holder.itemView.getContext())
                .load(myImg)
                .placeholder(R.drawable.ic_home)
                .into(holder.imageAd);*/
}

@Override
public int getItemCount() {
        return (myAds != null) ? myAds.size() : 0;
}

public AdTable getItem(int position) {
        return myAds.get(position);
}

        // CLASSE HOLDER
public class AdsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView adItemView;
        public ImageView modifyMyAd;
        AdTable ad;
        public ImageView imageAd;

        public AdsViewHolder(View itemView) {
                super(itemView);

                //locImmoDatabase = AppDatabase.getInstance(itemView.getContext());

                imageAd = itemView.findViewById(R.id.displayImageAd);
                adItemView = itemView.findViewById(R.id.textView);
                modifyMyAd = itemView.findViewById(R.id.modifyAd);
                // écouteur sur le bouton de modification (crayon)
                modifyMyAd.setOnClickListener(this);

                // Au clic sur un item, on veut voir les détails de l'annonce
                itemView.setOnClickListener(view -> {
                        notifyItemChanged(selectedPos);
                        selectedPos = getLayoutPosition();
                        notifyItemChanged(selectedPos);
                        Intent redirection = new Intent(view.getContext(), DetailsAdActivity.class);
                        // get user associated to an ad
                        redirection.putExtra("adId", ad.getId());
                        redirection.putExtra("userId", ad.getUserId());
                        view.getContext().startActivity(redirection);
                });
}

        public void bind(String text) {
                adItemView.setText(text);
        }

        // Au clic sur le bouton de modification (uniquement)
        @Override
        public void onClick(View view) {
                notifyItemChanged(selectedPos);
                selectedPos = getLayoutPosition();
                notifyItemChanged(selectedPos);
                Intent intent = new Intent(view.getContext(), ModifyAdActivity.class);
                intent.putExtra("adId", ad.getId());
                view.getContext().startActivity(intent);
        }


} // fin classe AdsViewHolder


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