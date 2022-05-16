package com.example.immoloc.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "city")
public class City {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "zone")
    public String zone;

    @ColumnInfo(name = "zip_code")
    public String zip_code;

    @ColumnInfo(name = "population")
    public int population;

    @ColumnInfo(name = "department")
    public String department;

    @ColumnInfo(name = "geographic_coordinates")
    public String geographic_coordinates;

    @ColumnInfo(name = "price_loc_m2")
    public double price_loc_m2;

    public int getId(){
        return id;
    }
    public void setId(int id) { this.id = id; }

    public String getName() {return name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }
    
    public String getZipCode() {
        return zip_code;
    }
    public void setZipCode(String zip_code) {
        this.zip_code = zip_code;
    }

    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population) {
        this.population = population;
    }

    public String getGeoCor() {
        return geographic_coordinates;
    }
    public void setGeoCor(String geographic_coordinates) {
        this.geographic_coordinates = geographic_coordinates;
    }


    public double getPriceLoc() {
        return price_loc_m2;
    }

    public void setPriceLoc(double price_loc_m2) {
        this.price_loc_m2 = price_loc_m2;
    }

}
