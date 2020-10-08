package com.anm.bslndmag.Model;

public class RegisterRequest {

    String name;
    String mobile;
    String email;
    String pin;
    String language;
    String device_id;
    String device_type;

    public RegisterRequest(String name, String mobile, String email, String pin, String language, String device_id, String device_type) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.pin = pin;
        this.language = language;
        this.device_id = device_id;
        this.device_type = device_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
