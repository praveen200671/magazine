package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfflinechildDetails {

//    @SerializedName("child_id")
//    @Expose
    String child_id;
//    @SerializedName("child_name")
//    @Expose
    String child_name;
//    @SerializedName("child_contact")
//    @Expose
    String child_contact;
//    @SerializedName("mother_name")
//    @Expose
    String mother_name;
//    @SerializedName("child_dob")
//    @Expose
    String child_dob;
//    @SerializedName("child_status")
//    @Expose
    String child_status;
//    @SerializedName("child_unq_id")
//    @Expose
    String child_unq_id;
//    @SerializedName("mothers_data")
//    @Expose
    ArrayList<ArrayList<ANMMotherData>> mothers_data;
//    @SerializedName("done_vaccines")
//    @Expose
    ArrayList<String>  done_vaccines ;
//    @SerializedName("today_due_vaccines")
//    @Expose
    ArrayList<String> today_due_vaccines ;
//    @SerializedName("future_due_vaccines")
//    @Expose
    ArrayList<String>  future_due_vaccines ;

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getChild_contact() {
        return child_contact;
    }

    public void setChild_contact(String child_contact) {
        this.child_contact = child_contact;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getChild_dob() {
        return child_dob;
    }

    public void setChild_dob(String child_dob) {
        this.child_dob = child_dob;
    }

    public String getChild_status() {
        return child_status;
    }

    public void setChild_status(String child_status) {
        this.child_status = child_status;
    }

    public String getChild_unq_id() {
        return child_unq_id;
    }

    public void setChild_unq_id(String child_unq_id) {
        this.child_unq_id = child_unq_id;
    }

    public ArrayList<ArrayList<ANMMotherData>> getMothers_data() {
        return mothers_data;
    }

    public void setMothers_data(ArrayList<ArrayList<ANMMotherData>> mothers_data) {
        this.mothers_data = mothers_data;
    }

    public ArrayList<String> getDone_vaccines() {
        return done_vaccines;
    }

    public void setDone_vaccines(ArrayList<String> done_vaccines) {
        this.done_vaccines = done_vaccines;
    }

    public ArrayList<String> getToday_due_vaccines() {
        return today_due_vaccines;
    }

    public void setToday_due_vaccines(ArrayList<String> today_due_vaccines) {
        this.today_due_vaccines = today_due_vaccines;
    }

    public ArrayList<String> getFuture_due_vaccines() {
        return future_due_vaccines;
    }

    public void setFuture_due_vaccines(ArrayList<String> future_due_vaccines) {
        this.future_due_vaccines = future_due_vaccines;
    }
}
