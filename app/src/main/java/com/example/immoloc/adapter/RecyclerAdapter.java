package com.example.immoloc.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immoloc.DataConverter;
import com.example.immoloc.DetailsAdActivity;
import com.example.immoloc.R;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.example.immoloc.database.ImageTable;
import com.example.immoloc.database.User;

import java.util.List;

public class RecyclerAdapter extends ListAdapter<AdTable, RecyclerAdapter.AdsViewHolderRec> {

    public List<AdTable> myAds;
    public String[] mColors = {"#e5dcd6","#fff5ee"};
    private int selectedPos = RecyclerView.NO_POSITION;
    public List<ImageTable> myImgs;
    AppDatabase locImmoDatabase;
    AdDao adDao;
    public ImageTable imagesL;


    public RecyclerAdapter(@NonNull DiffUtil.ItemCallback<AdTable> diffCallback, List<AdTable> data, List<ImageTable> dataimg) {
        super(diffCallback);
        this.myAds = data;
        this.myImgs = dataimg;
    }

    @NonNull
    @Override
    public AdsViewHolderRec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyview_list, parent, false);
        locImmoDatabase = AppDatabase.getInstance(parent.getContext());
        adDao = locImmoDatabase.adDao();
        return new AdsViewHolderRec(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdsViewHolderRec holder, int position) {
        AdTable ads = myAds.get(position);
        holder.ad = ads;
        AdTable current = getItem(position);

        // Je récupère le nom pour pouvoir l'afficher dans le listing
        String getUserName = adDao.getUserName(current.getUserId());

        // alterner avec les couleurs mColors le background des annonces
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
        // lorsqu'un item sera selectionné on changera le background momentanément
        holder.itemView.setSelected(selectedPos == position);

        holder.bind("Annonce n° "+current.getId()+"\n"+"Prix du bien= "+String.valueOf(current.getPrice())+
                "€"+"\nby user: " +getUserName);

        ImageTable images = (ImageTable) myImgs.get(position);
        holder.imageAd.setImageBitmap(DataConverter.convertByteArray2Img(images.getImage()));
        images.setImage(images.getImage());
    }

    @Override
    public int getItemCount() {
        return (myAds != null) ? myAds.size() : 0;
    }

    public AdTable getItem(int position) {
        return myAds.get(position);
    }

    public class AdsViewHolderRec extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public AdTable ad;
        public final TextView adItemView;
        public ImageView imageAd;


        public AdsViewHolderRec(@NonNull View itemView) {
            super(itemView);
            adItemView = itemView.findViewById(R.id.textView);
            imageAd = itemView.findViewById(R.id.displayImgAdListing);
            itemView.setOnClickListener(this);
            imageAd.setOnClickListener(this);
        }

        public void bind(String text) {
            adItemView.setText(text);
        }

        @Override
        public void onClick(View view) {
            // on a besoin de la position courante sinon n'affiche pas la bonne image lors du clic
            imagesL = (ImageTable) myImgs.get(getAdapterPosition());
            imagesL.setImage(imagesL.getImage());

            Intent redirection = new Intent(view.getContext(), DetailsAdActivity.class);
            redirection.putExtra("adId", ad.getId());
            redirection.putExtra("userId", ad.getUserId());
            redirection.putExtra("getImg", imagesL.getImage());
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
            view.getContext().startActivity(redirection);
        }
    }

public static class AdDiffTwo extends DiffUtil.ItemCallback<AdTable> {

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
