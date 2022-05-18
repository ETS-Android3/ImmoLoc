package com.example.immoloc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.immoloc.adapter.AdsListAdapter;
import com.example.immoloc.adapter.AdsViewHolder;
import com.example.immoloc.adapter.AdsViewModel;
import com.example.immoloc.database.AdTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyAdsActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int DELETE_WORD_ACTIVITY_REQUEST_CODE = 2;


    private AdsViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AdsListAdapter adapter = new AdsListAdapter(new AdsListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWordViewModel = new ViewModelProvider(this).get(AdsViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllAds().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

        /*
        // Au clic sur l'ajout d'un mot
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        // Au clic sur la supression d'un mot
        FloatingActionButton fab2 = findViewById(R.id.fabb);
        fab2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DeleteWordActivity.class);
            startActivityForResult(intent, DELETE_WORD_ACTIVITY_REQUEST_CODE);
        });
    }*/

    }// fin onCreate

      /*  public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                AdTable word = new AdTable(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
                mWordViewModel.insert(word);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
            if (requestCode == DELETE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                mWordViewModel.delete(data.getStringExtra(DeleteWordActivity.EXTRA_REPLY));
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.empty_not_saved,
                        Toast.LENGTH_LONG).show();
            }
        } */
    }
