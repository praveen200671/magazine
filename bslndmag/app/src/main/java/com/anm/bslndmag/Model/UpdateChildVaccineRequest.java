package com.anm.bslndmag.Model;

import java.util.ArrayList;

public class UpdateChildVaccineRequest {

    String uniqueid;
    String child_id;
    String vaccine_date;
    String select="true";
   String[]  vaccination_name;
   String vaccination_namestr;
   String vaccination_namestrcode;
    String captured_date;
    String onlinesubmitstatus;
    String offlineid="0";



    public String getVaccination_namestr() {
        return vaccination_namestr;
    }

    public void setVaccination_namestr(String vaccination_namestr) {
        this.vaccination_namestr = vaccination_namestr;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getVaccine_date() {
        return vaccine_date;
    }

    public void setVaccine_date(String vaccine_date) {
        this.vaccine_date = vaccine_date;
    }

//    public String getSelect() {
//        return select;
//    }
//
//    public void setSelect(String select) {
//        this.select = select;
//    }

//    public String[] getVaccinearray() {
//        return vaccinearray;
//    }
//
//    public void setVaccinearray(String[] vaccinearray) {
//        this.vaccinearray = vaccinearray;
//    }
//    public ArrayList<String> getVaccinearray() {
//        return vaccinearray;
//    }
//
//    public void setVaccinearray(ArrayList<String> vaccinearray) {
//        this.vaccinearray = vaccinearray;
//    }


//    public boolean isSelect() {
//        return select;
//    }
//
//    public void setSelect(boolean select) {
//        this.select = select;
//    }


    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String[] getVaccination_name() {
        return vaccination_name;
    }

    public void setVaccination_name(String[] vaccination_name) {
        this.vaccination_name = vaccination_name;
    }

    public String getCaptured_date() {
        return captured_date;
    }

    public void setCaptured_date(String captured_date) {
        this.captured_date = captured_date;
    }

    public String getVaccination_namestrcode() {
        return vaccination_namestrcode;
    }

    public void setVaccination_namestrcode(String vaccination_namestrcode) {
        this.vaccination_namestrcode = vaccination_namestrcode;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getOnlinesubmitstatus() {
        return onlinesubmitstatus;
    }

    public void setOnlinesubmitstatus(String onlinesubmitstatus) {
        this.onlinesubmitstatus = onlinesubmitstatus;
    }

    public String getOfflineid() {
        return offlineid;
    }

    public void setOfflineid(String offlineid) {
        this.offlineid = offlineid;
    }
}
