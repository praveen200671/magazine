package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DueVaccineResponse {
    @SerializedName("status")
    @Expose
    int status;

    @SerializedName("data")
    @Expose
    ArrayList<DueVaccinesList> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<DueVaccinesList> getData() {
        return data;
    }

    public void setData(ArrayList<DueVaccinesList> data) {
        this.data = data;
    }
}
