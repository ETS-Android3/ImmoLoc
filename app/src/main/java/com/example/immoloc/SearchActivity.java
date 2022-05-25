package com.example.immoloc;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.textfield.TextInputEditText;


public class SearchActivity extends AppCompatActivity {

    public CheckBox date_debut, date_fin;
    public TextInputEditText editDateDebut, editDateFin;

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

        // On met en place la valeur finale du listener
        priceRangeSeekBar.setOnRangeSeekbarFinalValueListener((minValue, maxValue) ->
                Log.d("CRS_PRICE=>", minValue + " : " + maxValue));


        /** AREA **/

        final TextView tvMinArea = findViewById(R.id.filter_surface_min);
        final TextView tvMaxArea = findViewById(R.id.filter_surface_max);
        areaRangeSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
            tvMinArea.setText(minValue+"m²");
            tvMaxArea.setText(maxValue+"m²");
        });
        areaRangeSeekBar.setOnRangeSeekbarFinalValueListener((minValue, maxValue) ->
                Log.d("CRS_AREA=>", minValue + " : " + maxValue));

    }



}
