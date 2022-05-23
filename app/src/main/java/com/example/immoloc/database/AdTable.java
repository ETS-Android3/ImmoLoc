package com.example.immoloc.database;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "adtable",
        foreignKeys =
                {@ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = City.class,
                        parentColumns = "id",
                        childColumns = "city_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onDelete = ForeignKey.CASCADE)})
public class AdTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(defaultValue = "0")
    public int id;

    @ColumnInfo(name = "user_id", defaultValue = "0")
    public int userId;

    @ColumnInfo(name = "category_id", defaultValue = "0")
    public int categoryId;

    @ColumnInfo(name = "city_id", defaultValue = "0")
    public int cityId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "contact")
    public String contact;

    @ColumnInfo(name = "date_debut_loc")
    public String dateDebut;

    @ColumnInfo(name = "date_fin_loc")
    public String dateFin;

    @ColumnInfo(name = "price", defaultValue = "0")
    public int price;

    @ColumnInfo(name = "area",defaultValue = "0")
    public int surface;

    @ColumnInfo(name="nbPieces")
    public String nbPieces;

    @ColumnInfo(name="adresse")
    public String adresse;

    @ColumnInfo(name="nbSalleDeau")
    public String nbSalleDeau;

    @ColumnInfo(name="nbChambres")
    public String nbChambres;

    public String getNbPieces() {
        return nbPieces;
    }

    public void setNbPieces(String nbPieces) {
        this.nbPieces = nbPieces;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNbSalleDeau() {
        return nbSalleDeau;
    }

    public void setNbSalleDeau(String nbSalleDeau) {
        this.nbSalleDeau = nbSalleDeau;
    }

    public String getNbChambres() {
        return nbChambres;
    }

    public void setNbChambres(String nbChambres) {
        this.nbChambres = nbChambres;
    }

    public static AdTable getAd(long id, Context application){
        return AppDatabase.getInstance(application).adDao().getAd(id);
    }

    public int getSurface() { return surface; }
    public void setSurface(int surface) { this.surface = surface; }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) { this.id = id; }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContact(){ return contact;}

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

}
