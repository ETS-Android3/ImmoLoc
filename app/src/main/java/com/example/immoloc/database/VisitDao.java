package com.example.immoloc.database;

import androidx.room.Query;

public interface VisitDao {

    // Nombre de fois qu'un utilisateur a visité une annonce donnée
    @Query("SELECT nb_times_visited FROM visit, ad, user WHERE visit.ad_id = ad.id AND visit.id = user.id")
    Visit findNbVisit();

}
