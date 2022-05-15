package com.example.immoloc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.immoloc.database.User;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    public TextView search;
    public Button mesAnnonces, ajouterAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationViewListener();

        // On récupère le nom de l'utilisateur que l'Activité Login nous a envoyé pour l'afficher
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("getUN");
            TextView tv = findViewById(R.id.titleHomePage);
            tv.setText(String.format(getString(R.string.titlePage), value));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawer = findViewById(R.id.drawer_layout);

        // Rendre les items du groupe cliquables
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
        // Au clic sur le bouton accueil du footer
        // à implémenter
        // Au clic sur le bouton profil du footer
        // à implémenter

      // Pour la déconnexion
      if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

      // Gestion des clics sur le menu latéral
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

              case R.id.nav_profile:
                  Toast.makeText(this, "Page pas encore implémentée", Toast.LENGTH_SHORT).show();
                  // à implémenter
                  //Intent intent = new Intent(this, Profile.class);
          }
          return true;
      });

      ajouterAnnonce = findViewById(R.id.ajouterAnnonce);
      ajouterAnnonce.setOnClickListener(view -> {
          Intent redir = new Intent(this, AddAd.class);
          startActivity(redir);
      });

    } // fin onCreate

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
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


}