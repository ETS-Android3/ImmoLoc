package com.example.immoloc.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immoloc.R;
import com.example.immoloc.database.AdTable;

import java.util.List;

public class RecyclerAdapter extends ListAdapter<AdTable, RecyclerAdapter.AdsViewHolderRec> {

    public List<AdTable> myAds;
    public String[] mColors = {"#e5dcd6","#fff5ee"};
    private int selectedPos = RecyclerView.NO_POSITION;


    public RecyclerAdapter(@NonNull DiffUtil.ItemCallback<AdTable> diffCallback, List<AdTable> data) {
        super(diffCallback);
        this.myAds = data;
    }

    @NonNull
    @Override
    public AdsViewHolderRec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyview_list, parent, false);
        return new AdsViewHolderRec(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AdsViewHolderRec holder, int position) {
        AdTable ads = myAds.get(position);
        holder.ad = ads;
        AdTable current = getItem(position);
        // alterner avec les couleurs mColors le background des annonces
        holder.itemView.setBackgroundColor(Color.parseColor(mColors[position % 2]));
        // lorsqu'un item sera selectionné on changera le background momentanément
        holder.itemView.setSelected(selectedPos == position);
        holder.bind("Annonce n° "+current.getId()+"\n"+"Prix du bien= "+String.valueOf(current.getPrice())+
                "€"+"\nby user:" +current.userId);
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

        public AdsViewHolderRec(@NonNull View itemView) {
            super(itemView);
            adItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);

        }

        public void bind(String text) {
            adItemView.setText(text);
        }

        @Override
        public void onClick(View view) {
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
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
