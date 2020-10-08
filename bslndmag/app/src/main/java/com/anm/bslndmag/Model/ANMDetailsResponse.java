package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ANMDetailsResponse {
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("user_id")
    @Expose
    String user_id;
    @SerializedName("first_name")
    @Expose
    String first_name;
    @SerializedName("is_active")
    @Expose
    String is_active;
    @SerializedName("last_login")
    @Expose
    String last_login;
    @SerializedName("created_at")
    @Expose
    String created_at;
    @SerializedName("anm_relation_id")
    @Expose
    String anm_relation_id;
    @SerializedName("anm_name")
    @Expose
    String anm_name;
    @SerializedName("territory_id")
    @Expose
    String territory_id;
    @SerializedName("locations")
    @Expose
    ArrayList<Locations> locations;

    public ArrayList<Locations> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Locations> locations) {
        this.locations = locations;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAnm_relation_id() {
        return anm_relation_id;
    }

    public void setAnm_relation_id(String anm_relation_id) {
        this.anm_relation_id = anm_relation_id;
    }

    public String getAnm_name() {
        return anm_name;
    }

    public void setAnm_name(String anm_name) {
        this.anm_name = anm_name;
    }

    public String getTerritory_id() {
        return territory_id;
    }

    public void setTerritory_id(String territory_id) {
        this.territory_id = territory_id;
    }

   /* public ArrayList<Locations> getLocationsArrayList() {
        return locations;
    }

    public void setLocationsArrayList(ArrayList<Locations> locations) {
        this.locations = locations;
    }*/
}
