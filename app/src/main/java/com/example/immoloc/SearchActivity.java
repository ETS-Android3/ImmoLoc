package com.example.immoloc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.immoloc.database.AdDao;
import com.example.immoloc.database.AdTable;
import com.example.immoloc.database.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    public CheckBox date_debut, date_fin;
    public TextInputEditText editDateDebut, editDateFin;
    public FloatingActionButton searchThisAd;
    public String getMinPrice, getMaxPrice, getMinArea, getMaxArea, realEstateType;
    public AppCompatButton house, apartment, villa;
    public TextInputEditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_real_estate);


        date_debut = findViewById(R.id.checkBox_dateDebut);
        date_fin = findViewById(R.id.checkBox_dateFin);

        editDateDebut = findViewById(R.id.textSearch_dateDebut);
        editDateFin = findViewById(R.id.textSearch_dateFin);

        date_debut.setOnClickListener(view -> {
            if (date_debut.isChecked()) {
                editDateDebut.setFocusableInTouchMode(true);
            } else {
                editDateDebut.setFocusableInTouchMode(false);
            }
        });

        date_fin.setOnClickListener(view -> {
            if (date_fin.isChecked()) {
                editDateFin.setFocusableInTouchMode(true);
            } else {
                editDateFin.setFocusableInTouchMode(false);
            }
        });

        // Initialisation des seekbar pour le prix et la surface du bien
        final CrystalRangeSeekbar priceRangeSeekBar = findViewById(R.id.seekbar_price);
        final CrystalRangeSeekbar areaRangeSeekBar = findViewById(R.id.seekbar_surface);

        /** PRICE **/
        // Valeur min et max à partir des textview
        final TextView tvMinPrice = findViewById(R.id.filter_price_min);
        final TextView tvMaxPrice = findViewById(R.id.filter_price_max);

        // On met en place le listener
        priceRangeSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            tvMinPrice.setText(minValue+"€");
            tvMaxPrice.setText(maxValue+"€");
        });

        // On met en place la valeur finale du listener et on les récupère
        priceRangeSeekBar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
        @Override
        public void finalValue(Number minValue, Number maxValue) {
            getMinPrice = String.valueOf(minValue);
            getMaxPrice = String.valueOf(maxValue);
        }
    });


        /** AREA **/
        final TextView tvMinArea = findViewById(R.id.filter_surface_min);
        final TextView tvMaxArea = findViewById(R.id.filter_surface_max);
        areaRangeSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            tvMinArea.setText(minValue+"m²");
            tvMaxArea.setText(maxValue+"m²");
        });
        areaRangeSeekBar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                getMinArea = String.valueOf(minValue);
                getMaxArea = String.valueOf(maxValue);
            }
        });

        /** REAL ESTATE TYPE **/
        apartment = findViewById(R.id.filter_apartment);
        house = findViewById(R.id.filter_house);
        villa = findViewById(R.id.filter_villa);

        apartment.setOnClickListener(view -> {
            realEstateType = "Appartement";
            apartment.setBackgroundColor(Color.parseColor("#d0f55f"));
            villa.setBackgroundResource(R.drawable.near_btn_selector);
            house.setBackgroundResource(R.drawable.near_btn_selector);
        });
        house.setOnClickListener(view -> {
            realEstateType = "Maison";
            house.setBackgroundColor(Color.parseColor("#d0f55f"));
            apartment.setBackgroundResource(R.drawable.near_btn_selector);
            villa.setBackgroundResource(R.drawable.near_btn_selector);
        });
        villa.setOnClickListener(view -> {
            realEstateType = "Villa";
            villa.setBackgroundColor(Color.parseColor("#d0f55f"));
            apartment.setBackgroundResource(R.drawable.near_btn_selector);
            house.setBackgroundResource(R.drawable.near_btn_selector);
        });



        /** CHERCHER L'ANNONCE EN CLIQUANT SUR LE BOUTON **/
        searchThisAd = findViewById(R.id.searchAnAdBtn);
        searchThisAd.setOnClickListener(view -> {

            /**CITY**/
            city = findViewById(R.id.ville_search);
            String getCity = city.getText().toString();


            if((getMinPrice == null | getMaxPrice == null) | (getMinArea == null & getMaxArea == null)){
                Toast.makeText(this, "Vous devez spécifier au moins une valeur minimale ou maximale pour " +
                        "le prix et la surface.", Toast.LENGTH_LONG).show();
            } else {
                if (realEstateType == null) {
                    Toast.makeText(this, "Merci d'indiquer le type de bien recherché.", Toast.LENGTH_SHORT).show();
                } else {
                    if (getCity == null | getCity.isEmpty()){
                        Toast.makeText(this, "Merci d'indiquer la ville souhaitée.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent redirection = new Intent(this, ResultsSearchActivity.class);
                        redirection.putExtra("prixmin", getMinPrice);
                        redirection.putExtra("prixmax", getMaxPrice);
                        redirection.putExtra("areamin", getMinArea);
                        redirection.putExtra("areamax", getMaxArea);
                        redirection.putExtra("cityname", getCity);
                        redirection.putExtra("typeproperty", realEstateType);
                        startActivity(redirection);
                    }
                }
            }
        });

    } // fin onCreate

}
