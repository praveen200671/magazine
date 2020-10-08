package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddmotherResponse {
    @SerializedName("status")
    @Expose
    int status;
    @SerializedName("message")
    @Expose
    String message;

//    @SerializedName("mothers_data")
//    @Expose
//    Mother_data mothers_data;



    @SerializedName("child_data")
    @Expose
    ChildData child_data;

//    public Mother_data getMothers_data() {
//        return mothers_data;
//    }
//
//    public void setMothers_data(Mother_data mothers_data) {
//        this.mothers_data = mothers_data;
//    }

    public ChildData getChild_data() {
        return child_data;
    }

    public void setChild_data(ChildData child_data) {
        this.child_data = child_data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
