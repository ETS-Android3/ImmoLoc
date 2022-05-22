package com.example.immoloc.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Index;

import com.example.immoloc.ModifyAdActivity;
import com.example.immoloc.R;
import com.example.immoloc.database.AdTable;
import java.util.List;

// CLASSE ADAPTER
public class AdsListAdapter extends ListAdapter<AdTable, AdsListAdapter.AdsViewHolder> {

        public ImageView imageView;
        SharedPreferences pref;
        public ImageView modifyMyAd;
        public List<AdTable> myAds;
        public String[] mColors = {"#e5dcd6","#fff5ee"};
        private int selectedPos = RecyclerView.NO_POSITION;

public AdsListAdapter(@NonNull DiffUtil.ItemCallback<AdTable> diffCallback, List<AdTable> data) {
        super(diffCallback);
        this.myAds = data;
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

                try {
                        AdTable ads = myAds.get(position);
                        holder.ad = ads; // annonce courante (très important)
                        AdTable current = getItem(position);

               /* SharedPreferences.Editor prefsEditor = pref.edit();
                String data = pref.getString("TheIdOfUser", null);*/


                        // alterner avec les couleurs mColors le background des annonces
                        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
                        // lorsqu'un item sera selectionné on changera le background momentanément
                        holder.itemView.setSelected(selectedPos == position);

                        holder.bind("Annonce n° " + current.getId() + "\n" + "Prix du bien= " + String.valueOf(current.getPrice()) +
                                "€" + "\nby user:" + current.userId); // à supr currentuserid
                } catch(IndexOutOfBoundsException e){
                        e.printStackTrace();
                }
                //holder.adItemView.setBackgroundResource(R.drawable.list_border);
                //Uri uri = Uri.parse(ajtAn.uri.toString()); //pour img to path
                // Glide.with(holder.itemView.getContext())
                //        .load(new File(uri.getPath()))
                //       .into(imageView);
                // à continuer pour les autres champs de l'annonce, à modifier pour l'user courant
        }

        /*@Override
        public int getItemCount() {
                return myAds.size();
        }*/

        // CLASSE HOLDER
public class AdsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public final TextView adItemView;
        public ImageView modifyMyAd;
        AdTable ad;

        public AdsViewHolder(View itemView) {
                super(itemView);
                adItemView = itemView.findViewById(R.id.textView);
                modifyMyAd = itemView.findViewById(R.id.modifyAd);
                // écouteur sur le bouton de modification (crayon)
                modifyMyAd.setOnClickListener(this);

                itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                notifyItemChanged(selectedPos);
                                selectedPos = getLayoutPosition();
                                notifyItemChanged(selectedPos);
                        }
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