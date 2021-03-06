package com.anm.bslndmag.Model;

import java.util.ArrayList;

public class SearchedChildDetails {
    String child_id;
    String mthr_id;
    String mother_name;
    String child_contact;
    String child_unq_id;
    String child_name;
    String child_dob;
    String child_status;
    String is_vacinated_before;
    String added_time;
    String last_updation_time;

    ArrayList<String> done_vaccines;
    ArrayList<String> today_due_vaccines;
    ArrayList<String> future_due_vaccines;

    public String getAdded_time() {
        return added_time;
    }

    public void setAdded_time(String added_time) {
        this.added_time = added_time;
    }

    public String getLast_updation_time() {
        return last_updation_time;
    }

    public void setLast_updation_time(String last_updation_time) {
        this.last_updation_time = last_updation_time;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getMthr_id() {
        return mthr_id;
    }

    public void setMthr_id(String mthr_id) {
        this.mthr_id = mthr_id;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getChild_contact() {
        return child_contact;
    }

    public void setChild_contact(String child_contact) {
        this.child_contact = child_contact;
    }

    public String getChild_unq_id() {
        return child_unq_id;
    }

    public void setChild_unq_id(String child_unq_id) {
        this.child_unq_id = child_unq_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
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

    public String getIs_vacinated_before() {
        return is_vacinated_before;
    }

    public void setIs_vacinated_before(String is_vacinated_before) {
        this.is_vacinated_before = is_vacinated_before;
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
