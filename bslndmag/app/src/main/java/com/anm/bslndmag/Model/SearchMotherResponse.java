package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchMotherResponse {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("data")
    @Expose
    SearchMotherChildData data;
    @SerializedName("message")
    @Expose
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SearchMotherChildData getData() {
        return data;
    }

    public void setData(SearchMotherChildData data) {
        this.data = data;
    }
}
