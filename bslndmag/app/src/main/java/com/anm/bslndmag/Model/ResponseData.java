package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {
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
        status = status;
    }

    public ANMData getData() {
        return data;
    }

    public void setData(ANMData data) {
        this.data = data;
    }
}
