package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

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
    private HomeResponseData data;

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

    public HomeResponseData getData() {
        return data;
    }

    public void setData(HomeResponseData data) {
        this.data = data;
    }
}
