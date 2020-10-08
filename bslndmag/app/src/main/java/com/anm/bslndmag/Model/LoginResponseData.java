package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseData {

    private String id;
    private String token;
    private String name;
    private String email;
    private String mobile;
    private String termsandcondition;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
