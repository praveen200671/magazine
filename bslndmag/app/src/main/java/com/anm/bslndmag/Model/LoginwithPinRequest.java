package com.anm.bslndmag.Model;

public class LoginwithPinRequest {
    String mobile;
    String language;
    String devicetype;
    String deviceid;
    String pin;

    public LoginwithPinRequest(String mobile, String language, String devicetype, String deviceid, String pin) {
        this.mobile = mobile;
        this.language = language;
        this.devicetype = devicetype;
        this.deviceid = deviceid;
        this.pin = pin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
