package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ANMChildData {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("data")
    @Expose
    ANMData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
