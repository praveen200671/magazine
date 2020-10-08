package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchChildResponse {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("data")
    @Expose
    SearchedChildDetails data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SearchedChildDetails getData() {
        return data;
    }

    public void setData(SearchedChildDetails data) {
        this.data = data;
    }
}
