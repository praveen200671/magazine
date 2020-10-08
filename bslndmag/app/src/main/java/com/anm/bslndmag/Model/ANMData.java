package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ANMData {

    @SerializedName("anm_data")
    @Expose
    ANMDetailsResponse anm_data;

    public ANMDetailsResponse getAnm_data() {
        return anm_data;
    }

    public void setAnm_data(ANMDetailsResponse anm_data) {
        this.anm_data = anm_data;
    }
}
