package com.example.immoloc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationViewListener();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = findViewById(R.id.drawer_layout);

        // Rendre les items cliquables
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Au clic sur le bouton recherche du footer
        search = findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent redirection = new Intent(v.getContext(), SearchActivity.class);
                startActivity(redirection);
            }
        });

      if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

      navigationView.bringToFront();
      navigationView.setNavigationItemSelectedListener(item -> {
          switch (item.getItemId()) {

              case R.id.nav_logout: {
                  Intent intent = new Intent(this, MainActivity.class);
                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  intent.putExtra("EXIT", true);
                  startActivity(intent);
                  Toast.makeText(this, "Vous êtes maintenant déconnecté", Toast.LENGTH_SHORT).show();
                  return true;
              }
              case R.id.nav_msg:
                  Toast.makeText(this, "Vous n'avez pas de messages", Toast.LENGTH_SHORT).show();
                  return true;
          }
          return true;
      });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed(); // ferme l'activité quand click sur retour
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("logoutcheck", "click done");

        switch (item.getItemId()) {

            case R.id.nav_logout: {
                Log.d("logoutcheck", "click done");
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                return true;
            }
            case R.id.nav_msg:
                Log.d("logoutcheck", "nav msg  ");
                Toast.makeText(this, "Clicked item one", Toast.LENGTH_SHORT).show();
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


}