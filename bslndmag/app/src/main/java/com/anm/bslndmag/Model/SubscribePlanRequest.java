package com.anm.bslndmag.Model;

public class SubscribePlanRequest {

    private String language;
    private String id;
    private String device_id;
    private String device_type;
    private String type;


    public SubscribePlanRequest(String language, String id, String device_id, String device_type,String type) {
        this.language = language;
        this.id = id;
        this.device_id = device_id;
        this.device_type = device_type;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

}
