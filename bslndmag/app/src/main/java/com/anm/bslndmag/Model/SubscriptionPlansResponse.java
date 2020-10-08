package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubscriptionPlansResponse {

    @SerializedName("status")
    @Expose
    private boolean status;


    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    @SerializedName("data")
    @Expose
    private ArrayList<SubscriptionPlansData> data;




    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public ArrayList<SubscriptionPlansData> getData() {
        return data;
    }

    public void setData(ArrayList<SubscriptionPlansData> data) {
        this.data = data;
    }
}
