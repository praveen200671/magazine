package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    @Expose
    private boolean status;


    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    @SerializedName("termsandcondition")
    @Expose
    private String termsandcondition;

    @SerializedName("privacypolicy")
    @Expose
    private String privacypolicy;

    public String getTermsandcondition() {
        return termsandcondition;
    }

    public void setTermsandcondition(String termsandcondition) {
        this.termsandcondition = termsandcondition;
    }

    public String getPrivacypolicy() {
        return privacypolicy;
    }

    public void setPrivacypolicy(String privacypolicy) {
        this.privacypolicy = privacypolicy;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @SerializedName("data")
    @Expose
    private LoginResponseData data;

    public LoginResponseData getData() {
        return data;
    }

    public void setData(LoginResponseData data) {
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
