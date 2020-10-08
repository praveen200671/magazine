package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfflinemotherChildResponse {

    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("data")
    @Expose
    ArrayList<OfflinechildDetails> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<OfflinechildDetails> getData() {
        return data;
    }

    public void setData(ArrayList<OfflinechildDetails> data) {
        this.data = data;
    }
}
