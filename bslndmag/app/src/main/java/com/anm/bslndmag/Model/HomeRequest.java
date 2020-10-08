package com.anm.bslndmag.Model;

public class HomeRequest {
    private String device_id;
    private String language;
    private String device_type;

    public HomeRequest(String device_id,String language,String device_type) {
        this.device_id = device_id;
        this.language = language;
        this.device_id = device_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}
