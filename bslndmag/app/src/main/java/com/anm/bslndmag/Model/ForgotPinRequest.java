package com.anm.bslndmag.Model;

public class ForgotPinRequest {

    private String mobile;
    private String device_id;
    private String language;
    private String device_type;
    private String otp;

    public ForgotPinRequest(String mobile, String device_id, String language, String device_type, String otp) {
        this.mobile = mobile;
        this.device_id = device_id;
        this.language = language;
        this.device_type = device_type;
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
