package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VaccinationlistResponse {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("data")
    @Expose
    ArrayList<Vacccine> data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Vacccine> getData() {
        return data;
    }

    public void setData(ArrayList<Vacccine> data) {
        this.data = data;
    }



}
